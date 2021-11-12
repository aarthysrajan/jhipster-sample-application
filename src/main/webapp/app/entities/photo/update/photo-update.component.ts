import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPhoto, Photo } from '../photo.model';
import { PhotoService } from '../service/photo.service';
import { IContest } from 'app/entities/contest/contest.model';
import { ContestService } from 'app/entities/contest/service/contest.service';
import { IParticipant } from 'app/entities/participant/participant.model';
import { ParticipantService } from 'app/entities/participant/service/participant.service';

@Component({
  selector: 'jhi-photo-update',
  templateUrl: './photo-update.component.html',
})
export class PhotoUpdateComponent implements OnInit {
  isSaving = false;

  contestsSharedCollection: IContest[] = [];
  participantsSharedCollection: IParticipant[] = [];

  editForm = this.fb.group({
    id: [],
    photoId: [],
    userId: [],
    picRating: [],
    genre: [],
    description: [],
    inContest: [],
    contestId: [],
    contests: [],
    participant: [],
  });

  constructor(
    protected photoService: PhotoService,
    protected contestService: ContestService,
    protected participantService: ParticipantService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ photo }) => {
      this.updateForm(photo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const photo = this.createFromForm();
    if (photo.id !== undefined) {
      this.subscribeToSaveResponse(this.photoService.update(photo));
    } else {
      this.subscribeToSaveResponse(this.photoService.create(photo));
    }
  }

  trackContestById(index: number, item: IContest): number {
    return item.id!;
  }

  trackParticipantById(index: number, item: IParticipant): number {
    return item.id!;
  }

  getSelectedContest(option: IContest, selectedVals?: IContest[]): IContest {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoto>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(photo: IPhoto): void {
    this.editForm.patchValue({
      id: photo.id,
      photoId: photo.photoId,
      userId: photo.userId,
      picRating: photo.picRating,
      genre: photo.genre,
      description: photo.description,
      inContest: photo.inContest,
      contestId: photo.contestId,
      contests: photo.contests,
      participant: photo.participant,
    });

    this.contestsSharedCollection = this.contestService.addContestToCollectionIfMissing(
      this.contestsSharedCollection,
      ...(photo.contests ?? [])
    );
    this.participantsSharedCollection = this.participantService.addParticipantToCollectionIfMissing(
      this.participantsSharedCollection,
      photo.participant
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contestService
      .query()
      .pipe(map((res: HttpResponse<IContest[]>) => res.body ?? []))
      .pipe(
        map((contests: IContest[]) =>
          this.contestService.addContestToCollectionIfMissing(contests, ...(this.editForm.get('contests')!.value ?? []))
        )
      )
      .subscribe((contests: IContest[]) => (this.contestsSharedCollection = contests));

    this.participantService
      .query()
      .pipe(map((res: HttpResponse<IParticipant[]>) => res.body ?? []))
      .pipe(
        map((participants: IParticipant[]) =>
          this.participantService.addParticipantToCollectionIfMissing(participants, this.editForm.get('participant')!.value)
        )
      )
      .subscribe((participants: IParticipant[]) => (this.participantsSharedCollection = participants));
  }

  protected createFromForm(): IPhoto {
    return {
      ...new Photo(),
      id: this.editForm.get(['id'])!.value,
      photoId: this.editForm.get(['photoId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      picRating: this.editForm.get(['picRating'])!.value,
      genre: this.editForm.get(['genre'])!.value,
      description: this.editForm.get(['description'])!.value,
      inContest: this.editForm.get(['inContest'])!.value,
      contestId: this.editForm.get(['contestId'])!.value,
      contests: this.editForm.get(['contests'])!.value,
      participant: this.editForm.get(['participant'])!.value,
    };
  }
}
