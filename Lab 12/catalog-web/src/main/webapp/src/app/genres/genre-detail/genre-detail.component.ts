import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';

import {GenreService} from "../shared/genre.service";
import {Genre} from "../shared/genre.model";

import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-genre-detail',
  templateUrl: './genre-detail.component.html',
  styleUrls: ['./genre-detail.component.css']
})
export class GenreDetailComponent implements OnInit {

  @Input() genre!: Genre;
  genres!: Genre[];


  constructor(private genreService: GenreService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap( (params: Params) =>
      this.genreService.getGenre( + params['id']))
    ).subscribe(genre => this.genre = genre!);
  }

  goBack(): void{
    this.location.back();
  }

  save(): void{
    this.genreService.updateGenre(this.genre)
      .subscribe(_ => this.goBack());
  }

}
