import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {MoviesService} from "../shared/movies.service";
import {Genre} from "../../genres/shared/genre.model";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-new.component.html',
  styleUrls: ['./movie-new.component.css']
})
export class MovieNewComponent implements OnInit {

  genres!: Genre[];
  selectedGenre!: Genre;

  constructor(private movieService: MoviesService, private router: Router) { }

  ngOnInit(): void {
    this.getGenres();
  }


  getGenres(){
    this.movieService.getGenres().subscribe(
      res => this.genres = res.genres,
      error => alert("GetGenres failed")
    );
  }

  saveMovie(title: string, director: string, productionCountry: string){
    let genreId = this.selectedGenre.id;
    this.movieService.saveMovie({id: 0, title, director, genreId: +genreId, productionCountry}).subscribe(
      res => this.router.navigate(['/movies']),
      error => alert("Invalid movie data")
    )
  }
}
