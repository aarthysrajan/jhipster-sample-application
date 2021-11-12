import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'participant',
        data: { pageTitle: 'jhipsterSampleApplicationApp.participant.home.title' },
        loadChildren: () => import('./participant/participant.module').then(m => m.ParticipantModule),
      },
      {
        path: 'photo',
        data: { pageTitle: 'jhipsterSampleApplicationApp.photo.home.title' },
        loadChildren: () => import('./photo/photo.module').then(m => m.PhotoModule),
      },
      {
        path: 'contest',
        data: { pageTitle: 'jhipsterSampleApplicationApp.contest.home.title' },
        loadChildren: () => import('./contest/contest.module').then(m => m.ContestModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
