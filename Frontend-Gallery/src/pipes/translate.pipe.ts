import {Pipe, PipeTransform} from '@angular/core';
import {ServiceTranslate} from "./ServiceTranslate";


@Pipe({
  name: 'translate',
  standalone: true
})
export class TranslatePipe extends ServiceTranslate implements PipeTransform {

  public language:string|null=localStorage.getItem("lang");


  transform(value: string): string {
    if(this.language===null){
      this.language="en";
    }
    if(this.language==="en"){
      return this.getEn(value) || value;
    }
    if(this.language==="ro"){
      return this.getRo(value) || value;
    }

    return value;
  }

}

