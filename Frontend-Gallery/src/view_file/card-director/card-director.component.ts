import {Component, Input} from '@angular/core';
import {ImageResponse} from "../../types/ImageResponse";
import {Director} from "../../types/Director";
import {Button} from "primeng/button";
import {NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'card-director',
  standalone: true,
  imports: [
    Button,
    NgOptimizedImage
  ],
  templateUrl: './card-director.component.html',
  styleUrl: './card-director.component.scss'
})
export class CardDirectorComponent {
  @Input() director!: Director;

}
