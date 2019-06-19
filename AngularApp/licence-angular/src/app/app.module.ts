import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { GraphsComponent } from './components/graphs/graphs.component';
import { ProgramsComponent } from './components/programs/programs.component';
import { ApiCommunicator } from './services/api-communicator.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '../../node_modules/@angular/common/http';
import { GraphItemComponent } from './components/graphs/graph-item/graph-item.component';
import { ProgramItemComponent } from './components/programs/program-item/program-item.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/login/login.component';
import { AuthenticationInterceptor } from './auth/authentication.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    GraphsComponent,
    ProgramsComponent,
    GraphItemComponent,
    ProgramItemComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AccordionModule.forRoot(),
    BrowserAnimationsModule,
    FormsModule
  ],
  providers: [ApiCommunicator, { provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true }, ],
  bootstrap: [AppComponent]
})
export class AppModule { }
