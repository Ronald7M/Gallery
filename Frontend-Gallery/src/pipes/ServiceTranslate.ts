import {Language} from "./Language";

export class ServiceTranslate extends Language {

  public  getRo(key: string): string {
    return this.getValue(key,this.ro) || key;
  }

  public  getEn(key: string): string {
    return this.getValue(key,this.en) || key;
  }



}
