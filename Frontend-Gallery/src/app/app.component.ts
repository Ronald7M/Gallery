import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavbarComponent} from '../navbar/navbar.component';
import {CommonModule} from '@angular/common';
import { MessageService } from 'primeng/api';
import {LoginComponent} from "../login/login.component";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    NavbarComponent,
    CommonModule,
    LoginComponent


  ],

  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [MessageService]
})
export class AppComponent implements OnInit {
  isToken:boolean=false;
  constructor() {

  }

  ngOnInit(): void {
    if(localStorage.getItem('token')){
      this.isToken=true;
    }
  }


  receivedLoginOutput($event: boolean) {

  }
}
