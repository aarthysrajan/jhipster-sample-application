import * as dayjs from 'dayjs';
import { IPhoto } from 'app/entities/photo/photo.model';
import { IContest } from 'app/entities/contest/contest.model';

export interface IParticipant {
  id?: number;
  fName?: string | null;
  mName?: string | null;
  lName?: string | null;
  userName?: string | null;
  userId?: number | null;
  dob?: dayjs.Dayjs | null;
  emailId?: string | null;
  phoneNo?: number | null;
  noOfPhotos?: number | null;
  profilePic?: string | null;
  userRating?: number | null;
  noOfContestsParticipated?: number | null;
  noOfContestsWon?: number | null;
  photos?: IPhoto[] | null;
  contests?: IContest[] | null;
}

export class Participant implements IParticipant {
  constructor(
    public id?: number,
    public fName?: string | null,
    public mName?: string | null,
    public lName?: string | null,
    public userName?: string | null,
    public userId?: number | null,
    public dob?: dayjs.Dayjs | null,
    public emailId?: string | null,
    public phoneNo?: number | null,
    public noOfPhotos?: number | null,
    public profilePic?: string | null,
    public userRating?: number | null,
    public noOfContestsParticipated?: number | null,
    public noOfContestsWon?: number | null,
    public photos?: IPhoto[] | null,
    public contests?: IContest[] | null
  ) {}
}

export function getParticipantIdentifier(participant: IParticipant): number | undefined {
  return participant.id;
}
