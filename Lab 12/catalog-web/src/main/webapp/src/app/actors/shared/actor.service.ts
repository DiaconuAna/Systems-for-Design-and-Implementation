import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

import {Actor} from "./actor.model";

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  private actorsUrl = 'http://localhost:8080/api/actors';

  constructor(private httpClient: HttpClient) { }

  getActors(): Observable<any>{
    var actors = this.httpClient.get<any>(this.actorsUrl);
    console.log("Actors: " + actors)
    return actors;
  }

  getActor(id: number): Observable<Actor| undefined>{
    return this.getActors().pipe(
      map( response => {
        let a;
        response.actors.forEach( (elem: Actor) => {
          if(elem.id === id){
            a = elem;
          }
        })
        return a;
      })
  );
  }


  saveActor(actor : Actor): Observable<any> {
    return this.httpClient
      .post<any>(this.actorsUrl, actor);
  }

  update(actor: any): Observable<Actor>{
    const url = `${this.actorsUrl}/${actor.id}`;
    return this.httpClient.put<Actor>(url, actor);
  }

  delete(id: number): Observable<any>{
    const url = `${this.actorsUrl}/${id}`;
    return this.httpClient.delete(url);
  }

  getSortedByPopularity(): Observable<any>{
    const url = `${this.actorsUrl}/sort/popularity`;
    return this.httpClient.get<any>(url);
  }

}
