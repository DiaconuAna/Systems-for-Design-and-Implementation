import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ActorsComponent} from "./actors/actors.component";
import {ActorNewComponent} from "./actors/actor-new/actor-new.component";
import {ActorDetailComponent} from "./actors/actor-detail/actor-detail.component";
import {ContractsComponent} from "./contracts/contracts.component";
import {ContractNewComponent} from "./contracts/contract-new/contract-new.component";
import {MoviesComponent} from "./movies/movies.component";
import {GenresComponent} from "./genres/genres.component";
import {GenreDetailComponent} from "./genres/genre-detail/genre-detail.component";
import {GenreNewComponent} from "./genres/genre-new/genre-new.component";
import {MovieNewComponent} from "./movies/movie-new/movie-new.component";
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";

const routes: Routes = [
  {path: 'actors', component: ActorsComponent},
  {path: 'actorsNew', component: ActorNewComponent},
  {path: 'actors/detail/:id', component: ActorDetailComponent},
  {path: 'contracts', component: ContractsComponent},
  {path: 'contractsNew', component: ContractNewComponent},
  {path: 'movies', component: MoviesComponent},
  {path: 'genres', component: GenresComponent},
  {path: 'genres/detail/:id', component: GenreDetailComponent},
  {path: 'genresNew', component: GenreNewComponent},
  {path: 'moviesNew', component: MovieNewComponent},
  {path: 'movies/detail/:id', component: MovieDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
