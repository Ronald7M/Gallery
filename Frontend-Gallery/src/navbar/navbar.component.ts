import { Component,OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import {RouterModule} from '@angular/router';
import {TranslatePipe} from "../pipes/translate.pipe";




@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [ButtonModule,RouterModule, TranslatePipe],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {


 ngOnInit(): void{

}
public changeLang(lang:string){
   localStorage.setItem("lang",lang);
    window.location.reload();
}





}
