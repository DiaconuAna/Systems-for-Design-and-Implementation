import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Contract} from "../shared/contract.model";
import {ContractService} from "../shared/contract.service";
import {Aggregate} from "../shared/movieActor.model";
import {Actor} from "../../actors/shared/actor.model";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";


@Component({
  selector: 'app-contract-list',
  templateUrl: './contract-list.component.html',
  styleUrls: ['./contract-list.component.css']
})
export class ContractListComponent implements OnInit {

  errorMessage!: string;
  contracts!: Contract[];
  selectedContract!: Contract;
  aggregates!: Aggregate[];
  currentActor!: Actor;

  constructor(private contractService: ContractService, private router: Router) { }

  ngOnInit(): void {
    this.getContracts();
    this.getAggregate();
  }


  getContracts(){
    this.contractService.getContracts().subscribe(
      res => {
        this.contracts = res.contracts;
      }
    );
  }

  onSelect(contract: Contract): void{
    this.selectedContract = contract;
  }

  // goToDetail(): void{
  //   this.router.navigate('/contracts/detail', this.selectedContract.id);
  // }

  getAggregate(){
    console.log("heyy")
    this.contractService.getAggregate().subscribe(
      res => {
        console.log(res.movieActors)
        this.aggregates = res.movieActors
      },//TODO: see here
      error => alert("Fail")
    );

  }
}
