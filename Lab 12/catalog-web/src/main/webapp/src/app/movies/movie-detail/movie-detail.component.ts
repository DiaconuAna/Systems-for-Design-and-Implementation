import {Component, Input, OnInit} from '@angular/core';

import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';

import {switchMap} from "rxjs/operators";

import {Movie} from "../shared/movie.model";
import {MoviesService} from "../shared/movies.service";
import {Genre} from "../../genres/shared/genre.model";
import {Observable} from "rxjs";

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {

  movie!: Movie;
  genres!: Genre[];
  selectedGenre!: Genre;
  movieGenre!: Genre;


  constructor(private movieService: MoviesService, private route: ActivatedRoute, private location: Location) { }

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap((params: Params) => this.movieService.getMovie( + params['id']))).
    subscribe(movie => this.movie = movie!);


    //this.getGenre()

    this.getGenres();
  }


  goBack(): void{
    this.location.back();
  }

  getGenres(){
    this.movieService.getGenres().subscribe(
      res => this.genres = res.genres,
      error => alert("GetGenres failed")
    );
  }

  // getGenre(){
  //   this.movieService.getGenre(this.movie.genreId).subscribe(
  //     res => this.movieGenre = res,
  //     error => alert("fail")
  //   )
  // }

  getGenre(): Genre{
    this.movieService.getGenre(this.movie.genreId).subscribe(
      res => this.movieGenre = res,
      error => alert("fail")
    );
    return this.movieGenre;
  }

  save(): void{
    this.movie.genreId = this.selectedGenre.id;
    this.movieService.updateMovie(this.movie)
      .subscribe(_ => this.goBack());
  }

}
