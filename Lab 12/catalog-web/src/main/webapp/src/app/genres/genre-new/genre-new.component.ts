import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

import {GenreService} from "../shared/genre.service";

@Component({
  selector: 'app-genre-new',
  templateUrl: './genre-new.component.html',
  styleUrls: ['./genre-new.component.css']
})
export class GenreNewComponent implements OnInit {

  constructor(private genreService: GenreService, private router: Router) { }

  ngOnInit(): void {
  }

  saveGenre(name: string, description: string){
    this.genreService.saveGenre({id: 0,name, description})
      .subscribe(
        res => this.router.navigate(['/genres']),
        error => alert("Invalid genre data")
      )
  }

}
