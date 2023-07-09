import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ContractService} from "../shared/contract.service";
import {Actor} from "../../actors/shared/actor.model";
import {Contract} from "../shared/contract.model";
import {Movie} from "../../movies/shared/movie.model";

@Component({
  selector: 'app-contract-new',
  templateUrl: './contract-new.component.html',
  styleUrls: ['./contract-new.component.css']
})
export class ContractNewComponent implements OnInit {

  actors!: Actor[];
  chosenActor!: Actor;
  movies!: Movie[];
  chosenMovie!: Movie;


  constructor(private contractService: ContractService, private router: Router) { }

  ngOnInit(): void {
    this.getActors();
    this.getMovies();
  }

  getActors(){
    this.contractService.getActors().
      subscribe(
       res => this.actors = res.actors
    );
  }

  getMovies(){
    this.contractService.getMovies().
      subscribe(
        res => this.movies = res.movies
    );
  }

  saveContract(){
    let actorId = this.chosenActor.id;
    let movieId = this.chosenMovie.id;

    this.contractService.saveContract({id: 0, actorId, movieId})
      .subscribe(
        res => this.router.navigate(['/contracts']),
        error => alert("Invalid contract data")
      );
  }
}
