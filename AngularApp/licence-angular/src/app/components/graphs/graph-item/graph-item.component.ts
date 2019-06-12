import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Graph } from '../../../models/graph';
import { ApiCommunicator } from '../../../services/api-communicator.service';

@Component({
  selector: 'app-graph-item',
  templateUrl: './graph-item.component.html',
  styleUrls: ['./graph-item.component.css']
})
export class GraphItemComponent implements OnInit {
  @Output() modified = new EventEmitter<void>();

  @Input()
  pending: boolean;

  @Input()
  graph: Graph;

  fileBody: string;

  constructor(private apiCommunicator: ApiCommunicator) {}

  ngOnInit() {
    this.apiCommunicator.getGraphBlob(this.graph.id).subscribe(event => {
      this.fileBody = event.text;
    });
  }

  onDelete() {
    this.apiCommunicator.deleteGraph(this.graph.id).subscribe(event => {
      this.modified.emit();
    });
  }

  onAccept() {
    this.apiCommunicator.acceptGraph(this.graph.id).subscribe(event => {
      this.modified.emit();
    });
  }
}
