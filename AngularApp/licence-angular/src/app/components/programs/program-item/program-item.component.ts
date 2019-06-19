import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Program } from '../../../models/program';
import { ApiCommunicator } from '../../../services/api-communicator.service';

@Component({
  selector: 'app-program-item',
  templateUrl: './program-item.component.html',
  styleUrls: ['./program-item.component.css']
})
export class ProgramItemComponent implements OnInit {
  @Output() modified = new EventEmitter<void>();


  @Input()
  pending: boolean;

  @Input()
  program: Program;

  fileBody: string;

  constructor(private apiCommunicator: ApiCommunicator) {}

  ngOnInit() {
    this.apiCommunicator.getProgramBlob(this.program.id).subscribe(event => {
      this.fileBody = event.text;
    });
  }

  onDelete() {
    this.apiCommunicator.deleteProgram(this.program.id).subscribe(event => {
      this.modified.emit();
    });
  }

  onAccept() {
    this.apiCommunicator.acceptProgram(this.program.id).subscribe(event => {
      this.modified.emit();
    });
  }
}
