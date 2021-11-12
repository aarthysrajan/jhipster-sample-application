import { IContest } from 'app/entities/contest/contest.model';
import { IParticipant } from 'app/entities/participant/participant.model';

export interface IPhoto {
  id?: number;
  photoId?: number | null;
  userId?: number | null;
  picRating?: number | null;
  genre?: string | null;
  description?: string | null;
  inContest?: boolean | null;
  contestId?: number | null;
  contests?: IContest[] | null;
  participant?: IParticipant | null;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public photoId?: number | null,
    public userId?: number | null,
    public picRating?: number | null,
    public genre?: string | null,
    public description?: string | null,
    public inContest?: boolean | null,
    public contestId?: number | null,
    public contests?: IContest[] | null,
    public participant?: IParticipant | null
  ) {
    this.inContest = this.inContest ?? false;
  }
}

export function getPhotoIdentifier(photo: IPhoto): number | undefined {
  return photo.id;
}
