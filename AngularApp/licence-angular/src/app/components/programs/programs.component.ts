import { Component, OnInit } from '@angular/core';
import { Program } from '../../models/program';
import { ApiCommunicator } from '../../services/api-communicator.service';

@Component({
  selector: 'app-programs',
  templateUrl: './programs.component.html',
  styleUrls: ['./programs.component.css']
})
export class ProgramsComponent implements OnInit {
  programs: Program[];

  buttonContent = 'Active Programs';
  pending = false;

  constructor(private apiCommunicator: ApiCommunicator) {}

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.apiCommunicator.getAllPrograms().subscribe(event => {
      this.programs = event.filter(program => program.pending === this.pending);
    });
  }

  onClick() {
    if (this.buttonContent === 'Pending Programs') {
      this.onActivePrograms();
    } else {
      this.onPendingPrograms();
    }
  }

  onPendingPrograms() {
    this.buttonContent = 'Pending Programs';
    this.pending = true;
    this.fetchData();
  }

  onActivePrograms() {
    this.buttonContent = 'Active Programs';
    this.pending = false;
    this.fetchData();
  }
}
