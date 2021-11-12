jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ParticipantService } from '../service/participant.service';
import { IParticipant, Participant } from '../participant.model';

import { ParticipantUpdateComponent } from './participant-update.component';

describe('Participant Management Update Component', () => {
  let comp: ParticipantUpdateComponent;
  let fixture: ComponentFixture<ParticipantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let participantService: ParticipantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ParticipantUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ParticipantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParticipantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    participantService = TestBed.inject(ParticipantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const participant: IParticipant = { id: 456 };

      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(participant));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Participant>>();
      const participant = { id: 123 };
      jest.spyOn(participantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(participantService.update).toHaveBeenCalledWith(participant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Participant>>();
      const participant = new Participant();
      jest.spyOn(participantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participant }));
      saveSubject.complete();

      // THEN
      expect(participantService.create).toHaveBeenCalledWith(participant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Participant>>();
      const participant = { id: 123 };
      jest.spyOn(participantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(participantService.update).toHaveBeenCalledWith(participant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
