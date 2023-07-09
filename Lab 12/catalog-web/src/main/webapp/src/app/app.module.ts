import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ActorsComponent } from './actors/actors.component';
import {RouterModule} from "@angular/router";
import { ActorListComponent } from './actors/actor-list/actor-list.component';
import {ActorService} from "./actors/shared/actor.service";
import { ActorDetailComponent } from './actors/actor-detail/actor-detail.component';
import { ActorNewComponent } from './actors/actor-new/actor-new.component';
import { ContractsComponent } from './contracts/contracts.component';
import { ContractListComponent } from './contracts/contract-list/contract-list.component';
import { ContractDetailComponent } from './contracts/contract-detail/contract-detail.component';
import { ContractNewComponent } from './contracts/contract-new/contract-new.component';
import { MoviesComponent } from './movies/movies.component';
import { MovieNewComponent } from './movies/movie-new/movie-new.component';
import { MovieDetailComponent } from './movies/movie-detail/movie-detail.component';
import { MovieListComponent } from './movies/movie-list/movie-list.component';
import { GenresComponent } from './genres/genres.component';
import { GenreListComponent } from './genres/genre-list/genre-list.component';
import { GenreNewComponent } from './genres/genre-new/genre-new.component';
import { GenreDetailComponent } from './genres/genre-detail/genre-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    ActorsComponent,
    ActorListComponent,
    ActorDetailComponent,
    ActorNewComponent,
    ContractsComponent,
    ContractListComponent,
    ContractDetailComponent,
    ContractNewComponent,
    MoviesComponent,
    MovieNewComponent,
    MovieDetailComponent,
    MovieListComponent,
    GenresComponent,
    GenreListComponent,
    GenreNewComponent,
    GenreDetailComponent,
  ],
  imports: [
    RouterModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [ActorService], //todo put services here
  bootstrap: [AppComponent]
})
export class AppModule { }
