import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IParticipant, Participant } from '../participant.model';

import { ParticipantService } from './participant.service';

describe('Participant Service', () => {
  let service: ParticipantService;
  let httpMock: HttpTestingController;
  let elemDefault: IParticipant;
  let expectedResult: IParticipant | IParticipant[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ParticipantService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      fName: 'AAAAAAA',
      mName: 'AAAAAAA',
      lName: 'AAAAAAA',
      userName: 'AAAAAAA',
      userId: 0,
      dob: currentDate,
      emailId: 'AAAAAAA',
      phoneNo: 0,
      noOfPhotos: 0,
      profilePic: 'AAAAAAA',
      userRating: 0,
      noOfContestsParticipated: 0,
      noOfContestsWon: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dob: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Participant', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dob: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.create(new Participant()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Participant', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fName: 'BBBBBB',
          mName: 'BBBBBB',
          lName: 'BBBBBB',
          userName: 'BBBBBB',
          userId: 1,
          dob: currentDate.format(DATE_FORMAT),
          emailId: 'BBBBBB',
          phoneNo: 1,
          noOfPhotos: 1,
          profilePic: 'BBBBBB',
          userRating: 1,
          noOfContestsParticipated: 1,
          noOfContestsWon: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Participant', () => {
      const patchObject = Object.assign(
        {
          fName: 'BBBBBB',
          mName: 'BBBBBB',
          userName: 'BBBBBB',
          emailId: 'BBBBBB',
          phoneNo: 1,
          noOfPhotos: 1,
          profilePic: 'BBBBBB',
        },
        new Participant()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Participant', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fName: 'BBBBBB',
          mName: 'BBBBBB',
          lName: 'BBBBBB',
          userName: 'BBBBBB',
          userId: 1,
          dob: currentDate.format(DATE_FORMAT),
          emailId: 'BBBBBB',
          phoneNo: 1,
          noOfPhotos: 1,
          profilePic: 'BBBBBB',
          userRating: 1,
          noOfContestsParticipated: 1,
          noOfContestsWon: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dob: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Participant', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addParticipantToCollectionIfMissing', () => {
      it('should add a Participant to an empty array', () => {
        const participant: IParticipant = { id: 123 };
        expectedResult = service.addParticipantToCollectionIfMissing([], participant);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(participant);
      });

      it('should not add a Participant to an array that contains it', () => {
        const participant: IParticipant = { id: 123 };
        const participantCollection: IParticipant[] = [
          {
            ...participant,
          },
          { id: 456 },
        ];
        expectedResult = service.addParticipantToCollectionIfMissing(participantCollection, participant);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Participant to an array that doesn't contain it", () => {
        const participant: IParticipant = { id: 123 };
        const participantCollection: IParticipant[] = [{ id: 456 }];
        expectedResult = service.addParticipantToCollectionIfMissing(participantCollection, participant);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(participant);
      });

      it('should add only unique Participant to an array', () => {
        const participantArray: IParticipant[] = [{ id: 123 }, { id: 456 }, { id: 98983 }];
        const participantCollection: IParticipant[] = [{ id: 123 }];
        expectedResult = service.addParticipantToCollectionIfMissing(participantCollection, ...participantArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const participant: IParticipant = { id: 123 };
        const participant2: IParticipant = { id: 456 };
        expectedResult = service.addParticipantToCollectionIfMissing([], participant, participant2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(participant);
        expect(expectedResult).toContain(participant2);
      });

      it('should accept null and undefined values', () => {
        const participant: IParticipant = { id: 123 };
        expectedResult = service.addParticipantToCollectionIfMissing([], null, participant, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(participant);
      });

      it('should return initial array if no Participant is added', () => {
        const participantCollection: IParticipant[] = [{ id: 123 }];
        expectedResult = service.addParticipantToCollectionIfMissing(participantCollection, undefined, null);
        expect(expectedResult).toEqual(participantCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
