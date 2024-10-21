import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "./ConfigService";
import {Credential} from "../types/Credential";
import {Observable} from "rxjs";
import {ImageResponse} from "../types/ImageResponse";

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private url:string;


  constructor(private http: HttpClient,private configService:ConfigService){
    this.url=configService.getApiUrl();
  }
  getImages(idDir:number): Observable<number[]> {
    const headers = this.configService.getHeader();
    return this.http.get<number[]>(this.url+'/images/'+idDir,{headers});
  }

  getImage(idDir:number,idPhoto:number,type:string): Observable<ImageResponse> {
    const headers = this.configService.getHeader();
    return this.http.get<ImageResponse>(this.url+'/images/'+idDir+'/'+idPhoto+'/'+type,{headers});
  }

  deleteImage(idDir:number): Observable<boolean> {
    const headers = this.configService.getHeader();
    return this.http.delete<boolean>(this.url+'/images/'+idDir,{headers});
  }

}
