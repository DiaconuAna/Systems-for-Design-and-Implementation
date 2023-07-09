import {Component, Input, OnInit} from '@angular/core';
import {ActorService} from "../shared/actor.service";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';
import {Actor} from "../shared/actor.model";

import {switchMap} from "rxjs/operators";


@Component({
  selector: 'app-actor-detail',
  templateUrl: './actor-detail.component.html',
  styleUrls: ['./actor-detail.component.css']
})
export class ActorDetailComponent implements OnInit {

  @Input() actor!: Actor;
  actors!: Actor[];
  gender = ['non-binary', 'female', 'male'];
  actorGender!: string;

  constructor(private actorService: ActorService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit(): void {

    this.route.params.pipe(
      switchMap((params: Params) => this.actorService.getActor( + params['id']))).
      subscribe(actor => this.actor = actor!);

  }

  goBack(): void{
    this.location.back();
  }

  save(): void{
    this.actorService.update(this.actor)
      .subscribe(_ => this.goBack());
  }

}
