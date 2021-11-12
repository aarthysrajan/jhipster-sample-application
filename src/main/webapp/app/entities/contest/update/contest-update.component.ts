import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IContest, Contest } from '../contest.model';
import { ContestService } from '../service/contest.service';
import { IParticipant } from 'app/entities/participant/participant.model';
import { ParticipantService } from 'app/entities/participant/service/participant.service';

@Component({
  selector: 'jhi-contest-update',
  templateUrl: './contest-update.component.html',
})
export class ContestUpdateComponent implements OnInit {
  isSaving = false;

  participantsSharedCollection: IParticipant[] = [];

  editForm = this.fb.group({
    id: [],
    contestId: [],
    contestName: [],
    contestDescription: [],
    ownerId: [],
    organization: [],
    emailId: [],
    contactNo: [],
    startDate: [],
    endDate: [],
    isActive: [],
    noOfPhotos: [],
    noOfParticipants: [],
    winnerId: [],
    publicVoting: [],
    participant: [],
  });

  constructor(
    protected contestService: ContestService,
    protected participantService: ParticipantService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contest }) => {
      this.updateForm(contest);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contest = this.createFromForm();
    if (contest.id !== undefined) {
      this.subscribeToSaveResponse(this.contestService.update(contest));
    } else {
      this.subscribeToSaveResponse(this.contestService.create(contest));
    }
  }

  trackParticipantById(index: number, item: IParticipant): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContest>>): void {
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

  protected updateForm(contest: IContest): void {
    this.editForm.patchValue({
      id: contest.id,
      contestId: contest.contestId,
      contestName: contest.contestName,
      contestDescription: contest.contestDescription,
      ownerId: contest.ownerId,
      organization: contest.organization,
      emailId: contest.emailId,
      contactNo: contest.contactNo,
      startDate: contest.startDate,
      endDate: contest.endDate,
      isActive: contest.isActive,
      noOfPhotos: contest.noOfPhotos,
      noOfParticipants: contest.noOfParticipants,
      winnerId: contest.winnerId,
      publicVoting: contest.publicVoting,
      participant: contest.participant,
    });

    this.participantsSharedCollection = this.participantService.addParticipantToCollectionIfMissing(
      this.participantsSharedCollection,
      contest.participant
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IContest {
    return {
      ...new Contest(),
      id: this.editForm.get(['id'])!.value,
      contestId: this.editForm.get(['contestId'])!.value,
      contestName: this.editForm.get(['contestName'])!.value,
      contestDescription: this.editForm.get(['contestDescription'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
      organization: this.editForm.get(['organization'])!.value,
      emailId: this.editForm.get(['emailId'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      noOfPhotos: this.editForm.get(['noOfPhotos'])!.value,
      noOfParticipants: this.editForm.get(['noOfParticipants'])!.value,
      winnerId: this.editForm.get(['winnerId'])!.value,
      publicVoting: this.editForm.get(['publicVoting'])!.value,
      participant: this.editForm.get(['participant'])!.value,
    };
  }
}
