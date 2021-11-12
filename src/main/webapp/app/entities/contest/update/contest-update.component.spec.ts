jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ContestService } from '../service/contest.service';
import { IContest, Contest } from '../contest.model';
import { IParticipant } from 'app/entities/participant/participant.model';
import { ParticipantService } from 'app/entities/participant/service/participant.service';

import { ContestUpdateComponent } from './contest-update.component';

describe('Contest Management Update Component', () => {
  let comp: ContestUpdateComponent;
  let fixture: ComponentFixture<ContestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contestService: ContestService;
  let participantService: ParticipantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ContestUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ContestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contestService = TestBed.inject(ContestService);
    participantService = TestBed.inject(ParticipantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Participant query and add missing value', () => {
      const contest: IContest = { id: 456 };
      const participant: IParticipant = { id: 28330 };
      contest.participant = participant;

      const participantCollection: IParticipant[] = [{ id: 97930 }];
      jest.spyOn(participantService, 'query').mockReturnValue(of(new HttpResponse({ body: participantCollection })));
      const additionalParticipants = [participant];
      const expectedCollection: IParticipant[] = [...additionalParticipants, ...participantCollection];
      jest.spyOn(participantService, 'addParticipantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      expect(participantService.query).toHaveBeenCalled();
      expect(participantService.addParticipantToCollectionIfMissing).toHaveBeenCalledWith(participantCollection, ...additionalParticipants);
      expect(comp.participantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contest: IContest = { id: 456 };
      const participant: IParticipant = { id: 51994 };
      contest.participant = participant;

      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(contest));
      expect(comp.participantsSharedCollection).toContain(participant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contest>>();
      const contest = { id: 123 };
      jest.spyOn(contestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contest }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(contestService.update).toHaveBeenCalledWith(contest);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contest>>();
      const contest = new Contest();
      jest.spyOn(contestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contest }));
      saveSubject.complete();

      // THEN
      expect(contestService.create).toHaveBeenCalledWith(contest);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Contest>>();
      const contest = { id: 123 };
      jest.spyOn(contestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contestService.update).toHaveBeenCalledWith(contest);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackParticipantById', () => {
      it('Should return tracked Participant primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackParticipantById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
