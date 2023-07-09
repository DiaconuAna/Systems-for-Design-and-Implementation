import { Component, OnInit } from '@angular/core';
import {ActorService} from "../shared/actor.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-actor-new',
  templateUrl: './actor-new.component.html',
  styleUrls: ['./actor-new.component.css']
})
export class ActorNewComponent implements OnInit {

  actorGender!: string;
  gender = ['non-binary', 'female', 'male'];

  constructor(private actorService: ActorService, private router: Router) { }

  ngOnInit(): void {
  }

  saveActor(firstName: string, lastName: string, age: string,  popularity: string){
    //console.log(this.actorGender)
    let gender = this.actorGender
    this.actorService.saveActor({id:0, firstName, lastName, age: +age, gender, popularity: +popularity})
      .subscribe(response =>{
        console.log(response);
        this.router.navigate(['/actors']);
      },
        error => alert("Invalid actor data"))
  }
}
