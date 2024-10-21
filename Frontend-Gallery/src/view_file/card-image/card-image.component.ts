import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ImageResponse} from "../../types/ImageResponse";
import {DialogModule} from "primeng/dialog";
import {Button} from "primeng/button";

@Component({
  selector: 'card-image',
  standalone: true,
  imports: [
    DialogModule,
    Button
  ],
  templateUrl: './card-image.component.html',
  styleUrl: './card-image.component.scss'
})
export class CardImageComponent {

  @Input() image!: ImageResponse;
  @Output() messageEvent = new EventEmitter<ImageResponse>();

  onClick() {
    this.sendMessage()
  }

  sendMessage() {
    this.messageEvent.emit(this.image);
    console.log(this.image)
  }


}
