import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "./ConfigService";
import {Credential} from "../types/Credential";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private url:string='/login';

  constructor(private http: HttpClient,private configService:ConfigService){
    this.url=configService.getApiUrl()+this.url;
  }
  login(credential:Credential): Observable<string> {
    return this.http.put<string>(this.url, credential,{ responseType: 'text' as 'json' });
  }

}
