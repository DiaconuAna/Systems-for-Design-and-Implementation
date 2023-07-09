import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { FormsModule } from '@angular/forms';

import {Movie} from "../shared/movie.model";
import {MoviesService} from "../shared/movies.service";
import {Genre} from "../../genres/shared/genre.model";

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  movies!: Movie[];
  selectedMovie!: Movie;
  movie!: Movie;
  genres!: Genre[];
  filteredMovies!: Movie[];
  selectedGenre!: Genre;
  genreId!: number;
  gender = ['non-binary', 'female', 'male'];


  constructor(private movieService: MoviesService, private router: Router) { }

  ngOnInit(): void {
    this.getMovies();
    this.getGenres();
  }

  getMovies(){
    this.movieService.getMovies().subscribe(
      res => this.movies = res.movies,
      error => alert("Get movies failed")
    )
  }

  getGenres(){
    this.movieService.getGenres().subscribe(
      res => this.genres = res.genres,
      error => alert("GetGenres failed")
    );
  }

  onSelect(movie: Movie): void{
    this.selectedMovie = movie;
  }

  goToDetail(movie: Movie){
    this.router.navigate(['/movies/detail', movie.id]);
  }

  deleteMovie(movie: Movie){
    this.movieService.deleteMovie(movie.id).subscribe(
      res => this.movies = this.movies.filter(m => m.id !== movie.id),
      error => alert("Delete failed")
    )
  }


  filterByGenre(){
    console.log("received: " + this.selectedGenre.id + " " + this.selectedGenre.name)
    this.movieService.filterByGenre(this.selectedGenre.id).subscribe(
      res => {this.filteredMovies = res.movies;
                   console.log(this.filteredMovies)},
      error => alert("failed")
    );
  }
}
