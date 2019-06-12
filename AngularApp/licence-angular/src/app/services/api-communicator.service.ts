import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Graph } from '../models/graph';
import { HttpClient } from '@angular/common/http';
import { Program } from '../models/program';

@Injectable()
export class ApiCommunicator {
  baseUrl: string;
  constructor(private http: HttpClient) {
    this.baseUrl = `${environment.urlAddress}`;
  }

  public getAllGraphs(): Observable<Graph[]> {
    return this.http.get<Graph[]>(`${this.baseUrl}/graphs`);
  }

  public deleteGraph(id) {
    return this.http.delete(`${this.baseUrl}/graphs?id=` + id);
  }

  public acceptGraph(id) {
    return this.http.get(`${this.baseUrl}/graphs/accept?id=` + id);
  }

  public getAllPrograms(): Observable<Program[]> {
    return this.http.get<Program[]>(`${this.baseUrl}/programs`);
  }

  public deleteProgram(id) {
    return this.http.delete(`${this.baseUrl}/programs?id=` + id);
  }

  public acceptProgram(id) {
    return this.http.get(`${this.baseUrl}/programs/accept?id=` + id);
  }

  public getGraphBlob(id): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/graphs/blob?id=` + id);
  }

  public getProgramBlob(id): Observable<any> {
    return this.http.get<string>(`${this.baseUrl}/programs/blob?id=` + id);
  }
}
