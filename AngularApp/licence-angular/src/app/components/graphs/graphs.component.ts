import { Component, OnInit } from '@angular/core';
import { ApiCommunicator } from '../../services/api-communicator.service';
import { Graph } from '../../models/graph';

@Component({
  selector: 'app-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.css']
})
export class GraphsComponent implements OnInit {
  graphs: Graph[];
  buttonContent = 'Active Graphs';
  pending = false;

  constructor(private apiCommunicator: ApiCommunicator) {}

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.apiCommunicator.getAllGraphs().subscribe(event => {
      this.graphs = event.filter(graph => graph.pending === this.pending);
    });
  }

  onClick() {
    if (this.buttonContent === 'Pending Graphs') {
      this.onActiveGraphs();
    } else {
      this.onPendingGraphs();
    }
  }

  onPendingGraphs() {
    this.buttonContent = 'Pending Graphs';
    this.pending = true;
    this.fetchData();
  }

  onActiveGraphs() {
    this.buttonContent = 'Active Graphs';
    this.pending = false;
    this.fetchData();
  }
}
