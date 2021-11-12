import * as dayjs from 'dayjs';
import { IParticipant } from 'app/entities/participant/participant.model';
import { IPhoto } from 'app/entities/photo/photo.model';

export interface IContest {
  id?: number;
  contestId?: number | null;
  contestName?: string | null;
  contestDescription?: string | null;
  ownerId?: number | null;
  organization?: string | null;
  emailId?: string | null;
  contactNo?: number | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  noOfPhotos?: number | null;
  noOfParticipants?: number | null;
  winnerId?: string | null;
  publicVoting?: boolean | null;
  participant?: IParticipant | null;
  photos?: IPhoto[] | null;
}

export class Contest implements IContest {
  constructor(
    public id?: number,
    public contestId?: number | null,
    public contestName?: string | null,
    public contestDescription?: string | null,
    public ownerId?: number | null,
    public organization?: string | null,
    public emailId?: string | null,
    public contactNo?: number | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public isActive?: boolean | null,
    public noOfPhotos?: number | null,
    public noOfParticipants?: number | null,
    public winnerId?: string | null,
    public publicVoting?: boolean | null,
    public participant?: IParticipant | null,
    public photos?: IPhoto[] | null
  ) {
    this.isActive = this.isActive ?? false;
    this.publicVoting = this.publicVoting ?? false;
  }
}

export function getContestIdentifier(contest: IContest): number | undefined {
  return contest.id;
}
