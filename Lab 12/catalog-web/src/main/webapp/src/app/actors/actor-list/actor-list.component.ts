import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Actor} from "../shared/actor.model";
import {ActorService} from "../shared/actor.service";

@Component({
  selector: 'app-actor-list',
  templateUrl: './actor-list.component.html',
  styleUrls: ['./actor-list.component.css']
})
export class ActorListComponent implements OnInit {

  errorMessage!: string;
  actors!: Actor[];
  selectedActor!: Actor;

  constructor(private actorService: ActorService, private router: Router) { }

  ngOnInit(): void {
    this.getActors();
  }

  getActors(){
    this.actorService.getActors()
      .subscribe(
        response => {
          console.log("ACTORS V2: " + response);
          this.actors = response.actors;
        }
      );
  }

  onSelect(actor: Actor): void{
    this.selectedActor = actor;
  }

  goToDetail(): void{
    this.router.navigate(['/actors/detail', this.selectedActor.id]);
  }

  goToDetailAux(actor: Actor): void{
    this.router.navigate(['/actors/detail', actor.id]);
  }

  deleteActor(actor: Actor){
    this.actorService.delete(actor.id).subscribe(
      res =>{
        this.actors = this.actors.filter(a => a.id !== actor.id);
      }
    );
  }

  sortByPopularity(){
    this.actorService.getSortedByPopularity().subscribe(
      res => this.actors = res.actors,
      error => alert("Sth went wrong")
    );
  }

}
