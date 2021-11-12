import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IParticipant, Participant } from '../participant.model';
import { ParticipantService } from '../service/participant.service';

@Component({
  selector: 'jhi-participant-update',
  templateUrl: './participant-update.component.html',
})
export class ParticipantUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fName: [],
    mName: [],
    lName: [],
    userName: [],
    userId: [],
    dob: [],
    emailId: [],
    phoneNo: [],
    noOfPhotos: [],
    profilePic: [],
    userRating: [],
    noOfContestsParticipated: [],
    noOfContestsWon: [],
  });

  constructor(protected participantService: ParticipantService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participant }) => {
      this.updateForm(participant);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const participant = this.createFromForm();
    if (participant.id !== undefined) {
      this.subscribeToSaveResponse(this.participantService.update(participant));
    } else {
      this.subscribeToSaveResponse(this.participantService.create(participant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipant>>): void {
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

  protected updateForm(participant: IParticipant): void {
    this.editForm.patchValue({
      id: participant.id,
      fName: participant.fName,
      mName: participant.mName,
      lName: participant.lName,
      userName: participant.userName,
      userId: participant.userId,
      dob: participant.dob,
      emailId: participant.emailId,
      phoneNo: participant.phoneNo,
      noOfPhotos: participant.noOfPhotos,
      profilePic: participant.profilePic,
      userRating: participant.userRating,
      noOfContestsParticipated: participant.noOfContestsParticipated,
      noOfContestsWon: participant.noOfContestsWon,
    });
  }

  protected createFromForm(): IParticipant {
    return {
      ...new Participant(),
      id: this.editForm.get(['id'])!.value,
      fName: this.editForm.get(['fName'])!.value,
      mName: this.editForm.get(['mName'])!.value,
      lName: this.editForm.get(['lName'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      emailId: this.editForm.get(['emailId'])!.value,
      phoneNo: this.editForm.get(['phoneNo'])!.value,
      noOfPhotos: this.editForm.get(['noOfPhotos'])!.value,
      profilePic: this.editForm.get(['profilePic'])!.value,
      userRating: this.editForm.get(['userRating'])!.value,
      noOfContestsParticipated: this.editForm.get(['noOfContestsParticipated'])!.value,
      noOfContestsWon: this.editForm.get(['noOfContestsWon'])!.value,
    };
  }
}
