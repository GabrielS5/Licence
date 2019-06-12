import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GraphsComponent } from './components/graphs/graphs.component';
import { ProgramsComponent } from './components/programs/programs.component';
import { LoginComponent } from './components/login/login.component';
import { AuthenticationGuard } from './auth/authentication.guard';

const routes: Routes = [
  {
    path: '',
    component: ProgramsComponent,
    canActivate: [AuthenticationGuard]
  },
  {
    path: 'graphs',
    component: GraphsComponent,
    canActivate: [AuthenticationGuard]
  },
  {
    path: 'programs',
    component: ProgramsComponent,
    canActivate: [AuthenticationGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
