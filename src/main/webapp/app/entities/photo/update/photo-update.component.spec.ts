jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PhotoService } from '../service/photo.service';
import { IPhoto, Photo } from '../photo.model';
import { IContest } from 'app/entities/contest/contest.model';
import { ContestService } from 'app/entities/contest/service/contest.service';
import { IParticipant } from 'app/entities/participant/participant.model';
import { ParticipantService } from 'app/entities/participant/service/participant.service';

import { PhotoUpdateComponent } from './photo-update.component';

describe('Photo Management Update Component', () => {
  let comp: PhotoUpdateComponent;
  let fixture: ComponentFixture<PhotoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let photoService: PhotoService;
  let contestService: ContestService;
  let participantService: ParticipantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PhotoUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PhotoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PhotoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    photoService = TestBed.inject(PhotoService);
    contestService = TestBed.inject(ContestService);
    participantService = TestBed.inject(ParticipantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contest query and add missing value', () => {
      const photo: IPhoto = { id: 456 };
      const contests: IContest[] = [{ id: 74735 }];
      photo.contests = contests;

      const contestCollection: IContest[] = [{ id: 2232 }];
      jest.spyOn(contestService, 'query').mockReturnValue(of(new HttpResponse({ body: contestCollection })));
      const additionalContests = [...contests];
      const expectedCollection: IContest[] = [...additionalContests, ...contestCollection];
      jest.spyOn(contestService, 'addContestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ photo });
      comp.ngOnInit();

      expect(contestService.query).toHaveBeenCalled();
      expect(contestService.addContestToCollectionIfMissing).toHaveBeenCalledWith(contestCollection, ...additionalContests);
      expect(comp.contestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Participant query and add missing value', () => {
      const photo: IPhoto = { id: 456 };
      const participant: IParticipant = { id: 18924 };
      photo.participant = participant;

      const participantCollection: IParticipant[] = [{ id: 70327 }];
      jest.spyOn(participantService, 'query').mockReturnValue(of(new HttpResponse({ body: participantCollection })));
      const additionalParticipants = [participant];
      const expectedCollection: IParticipant[] = [...additionalParticipants, ...participantCollection];
      jest.spyOn(participantService, 'addParticipantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ photo });
      comp.ngOnInit();

      expect(participantService.query).toHaveBeenCalled();
      expect(participantService.addParticipantToCollectionIfMissing).toHaveBeenCalledWith(participantCollection, ...additionalParticipants);
      expect(comp.participantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const photo: IPhoto = { id: 456 };
      const contests: IContest = { id: 30986 };
      photo.contests = [contests];
      const participant: IParticipant = { id: 33981 };
      photo.participant = participant;

      activatedRoute.data = of({ photo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(photo));
      expect(comp.contestsSharedCollection).toContain(contests);
      expect(comp.participantsSharedCollection).toContain(participant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Photo>>();
      const photo = { id: 123 };
      jest.spyOn(photoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ photo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: photo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(photoService.update).toHaveBeenCalledWith(photo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Photo>>();
      const photo = new Photo();
      jest.spyOn(photoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ photo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: photo }));
      saveSubject.complete();

      // THEN
      expect(photoService.create).toHaveBeenCalledWith(photo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Photo>>();
      const photo = { id: 123 };
      jest.spyOn(photoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ photo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(photoService.update).toHaveBeenCalledWith(photo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackContestById', () => {
      it('Should return tracked Contest primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackContestById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackParticipantById', () => {
      it('Should return tracked Participant primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackParticipantById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedContest', () => {
      it('Should return option if no Contest is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedContest(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected Contest for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedContest(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this Contest is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedContest(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
