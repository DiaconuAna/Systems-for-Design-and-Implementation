import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Contract} from "./contract.model";
import {map} from "rxjs/operators";
import {Actor} from "../../actors/shared/actor.model";


@Injectable({
  providedIn: 'root'
})
export class ContractService {

  private contractsUrl = "http://localhost:8080/api/contracts";
  private moviesUrl = 'http://localhost:8080/api/movies';
  private actorsUrl = 'http://localhost:8080/api/actors';


  constructor(private httpClient: HttpClient) { }

  getContracts(): Observable<any>{
    return this.httpClient.get<any>(this.contractsUrl);
  }

  getActors(): Observable<any>{
    return this.httpClient.get<any>(this.actorsUrl);
  }

  getActor(id: number): Observable<Actor>{
    // @ts-ignore
    return this.getActors().pipe(
      map( response => {
        let a;
        response.actors.forEach( (elem: Actor) => {
          if(elem.id === id){
            a = elem;
          }
        })
        return a;
      })
    );
  }

  getMovies(): Observable<any>{
    return this.httpClient.get<any>(this.moviesUrl);
  }

  getContract(id: number): Observable<Contract|undefined>{
    return this.getContracts().pipe(
      map( res =>{
          let c;

          res.contracts.forEach( (elem: Contract) => {
            if(elem.id == id)
              c = elem;
          })
          return c;
        })
    );
  }

  saveContract(contract: Contract): Observable<any>{
    return this.httpClient.post<any>(this.contractsUrl, contract);
  }

  delete(id: number): Observable<any>{
    const url = `${this.contractsUrl}/${id}`;
    return this.httpClient.delete(url);
  }

  getAggregate(){
    console.log("hello")
    const url = `${this.contractsUrl}/getAggregate`;
    var res = this.httpClient.get<any>(url);
    console.log("res: " + res);
    return res;
  }
}
