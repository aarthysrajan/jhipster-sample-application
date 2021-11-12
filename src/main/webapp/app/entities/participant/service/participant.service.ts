import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParticipant, getParticipantIdentifier } from '../participant.model';

export type EntityResponseType = HttpResponse<IParticipant>;
export type EntityArrayResponseType = HttpResponse<IParticipant[]>;

@Injectable({ providedIn: 'root' })
export class ParticipantService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/participants');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(participant: IParticipant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participant);
    return this.http
      .post<IParticipant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(participant: IParticipant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participant);
    return this.http
      .put<IParticipant>(`${this.resourceUrl}/${getParticipantIdentifier(participant) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(participant: IParticipant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participant);
    return this.http
      .patch<IParticipant>(`${this.resourceUrl}/${getParticipantIdentifier(participant) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParticipant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParticipant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParticipantToCollectionIfMissing(
    participantCollection: IParticipant[],
    ...participantsToCheck: (IParticipant | null | undefined)[]
  ): IParticipant[] {
    const participants: IParticipant[] = participantsToCheck.filter(isPresent);
    if (participants.length > 0) {
      const participantCollectionIdentifiers = participantCollection.map(participantItem => getParticipantIdentifier(participantItem)!);
      const participantsToAdd = participants.filter(participantItem => {
        const participantIdentifier = getParticipantIdentifier(participantItem);
        if (participantIdentifier == null || participantCollectionIdentifiers.includes(participantIdentifier)) {
          return false;
        }
        participantCollectionIdentifiers.push(participantIdentifier);
        return true;
      });
      return [...participantsToAdd, ...participantCollection];
    }
    return participantCollection;
  }

  protected convertDateFromClient(participant: IParticipant): IParticipant {
    return Object.assign({}, participant, {
      dob: participant.dob?.isValid() ? participant.dob.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dob = res.body.dob ? dayjs(res.body.dob) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((participant: IParticipant) => {
        participant.dob = participant.dob ? dayjs(participant.dob) : undefined;
      });
    }
    return res;
  }
}
