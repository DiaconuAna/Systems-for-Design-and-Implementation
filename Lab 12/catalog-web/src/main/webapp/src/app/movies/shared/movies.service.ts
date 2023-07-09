import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

import {Movie} from "./movie.model";
import {Actor} from "../../actors/shared/actor.model";
import {Genre} from "../../genres/shared/genre.model";

@Injectable({
  providedIn: 'root'
})
export class MoviesService {

  private moviesUrl = 'http://localhost:8080/api/movies';
  private genresUrl = 'http://localhost:8080/api/genres';


  constructor(private httpClient: HttpClient) { }

  getMovies(): Observable<any>{
    return this.httpClient.get<any>(this.moviesUrl);
  }

  getGenres(): Observable<any>{
    return this.httpClient.get<any>(this.genresUrl);
  }

  getMovie(id: number): Observable<Movie | undefined>{
    return this.getMovies().pipe(
      map( response => {
        let m;
        response.movies.forEach( (elem: Movie) => {
          if(elem.id === id){
            m = elem;
          }
        })
        return m;
      })
    );
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

  saveMovie(movie: Movie): Observable<any>{
    return this.httpClient.post<any>(this.moviesUrl, movie);
  }

  updateMovie(movie: any): Observable<Movie>{
    const url = `${this.moviesUrl}/${movie.id}`;
    return this.httpClient.put<Movie>(url, movie);
  }

  deleteMovie(id: number): Observable<any>{
    const url = `${this.moviesUrl}/${id}`;
    return this.httpClient.delete(url);
  }

  //to do - filter by genre - dropdown with genres
  filterByGenre(id: number): Observable<any>{
    console.log("ID = " + id + "\n");
    const url = `${this.moviesUrl}/filter/${id}`;
    console.log(url)
    return this.httpClient.get<any>(url);
  }
}
