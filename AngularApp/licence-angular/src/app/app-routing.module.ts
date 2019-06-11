import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GraphsComponent } from './components/graphs/graphs.component';
import { ProgramsComponent } from './components/programs/programs.component';

const routes: Routes = [
  {
    path: '',
    component: ProgramsComponent
  },
  {
    path: 'graphs',
    component: GraphsComponent
  },
  {
    path: 'programs',
    component: ProgramsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
