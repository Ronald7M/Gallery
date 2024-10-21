import { Injectable } from '@angular/core';
import {environment} from "../environments/environment";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private readonly apiUrl=environment.apiUrl;

  public getHeader(): HttpHeaders{
    const token = localStorage.getItem("token");
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return headers;

  }

  public getApiUrl(): string {
    return this.apiUrl;
    console.log(this.apiUrl)
  }
}
