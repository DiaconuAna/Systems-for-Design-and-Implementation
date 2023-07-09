import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

import {Genre} from "./genre.model";

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  private genresUrl = 'http://localhost:8080/api/genres';

  constructor(private httpClient: HttpClient) { }

  getGenres(): Observable<any>{
    return this.httpClient.get<any>(this.genresUrl);
  }

  getGenre(id: number): Observable<Genre>{
    // @ts-ignore
    return this.getGenres().pipe(
      map(
        res => {
          let g;
          res.genres.forEach( (elem: Genre) => {
            if(elem.id === id){
              g = elem;
            }
          })
          return g;
        })
    );
  }

  saveGenre(genre: Genre): Observable<any>{
    return this.httpClient.post<any>(this.genresUrl, genre);
  }

  updateGenre(genre: any): Observable<Genre>{
    const url = `${this.genresUrl}/${genre.id}`;
    return this.httpClient.put<Genre>(url, genre);
  }

  deleteGenre(id: number): Observable<any>{
    const url = `${this.genresUrl}/${id}`;
    return this.httpClient.delete(url);
  }
}
