import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AccordionModule } from 'ngx-bootstrap/accordion';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { GraphsComponent } from './components/graphs/graphs.component';
import { ProgramsComponent } from './components/programs/programs.component';
import { ApiCommunicator } from './services/api-communicator.service';
import { HttpClientModule } from '../../node_modules/@angular/common/http';
import { GraphItemComponent } from './components/graphs/graph-item/graph-item.component';
import { ProgramItemComponent } from './components/programs/program-item/program-item.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    GraphsComponent,
    ProgramsComponent,
    GraphItemComponent,
    ProgramItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AccordionModule.forRoot(),
    BrowserAnimationsModule
  ],
  providers: [ApiCommunicator],
  bootstrap: [AppComponent]
})
export class AppModule { }
