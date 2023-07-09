import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

import {Genre} from "../shared/genre.model";
import {GenreService} from "../shared/genre.service";

@Component({
  selector: 'app-genre-list',
  templateUrl: './genre-list.component.html',
  styleUrls: ['./genre-list.component.css']
})
export class GenreListComponent implements OnInit {

  errorMessage!: string;
  genres!: Genre[];
  selectedGenre!: Genre;

  constructor(private genreService: GenreService, private router: Router) { }

  ngOnInit(): void {
    this.getGenres();
  }

  getGenres(){
    this.genreService.getGenres().subscribe(
      res => this.genres = res.genres,
      error => alert("GetGenres failed")
    );
  }

  onSelect(genre: Genre): void{
    this.selectedGenre = genre;
  }

  goToDetail(genre: Genre): void{
    this.router.navigate(['/genres/detail', genre.id]);
  }

  deleteGenre(genre: Genre){
    this.genreService.deleteGenre(genre.id).subscribe(
      res => this.genres = this.genres.filter(g => g.id !== genre.id),
      error => alert("Delete genre failed")
    );
  }
}
