import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "./ConfigService";
import {Director} from "../types/Director";

@Injectable({
  providedIn: 'root'
})
export class DirectorService {
  private url:string;


  constructor(private http: HttpClient,private configService:ConfigService){
    this.url=configService.getApiUrl();
  }

  getDirectors(): Observable<Director[]> {
    const headers = this.configService.getHeader();
    return this.http.get<Director[]>(this.url+'/directors',{headers});
  }

  createDirector(nameDir:string): Observable<boolean> {
    const headers = this.configService.getHeader();
    return this.http.post<boolean>(this.url+'/directors/'+nameDir, null,{headers});
  }
}
