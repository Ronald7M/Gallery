import {Component, OnInit} from '@angular/core';
import {ImageResponse} from "../../types/ImageResponse";
import {ImageService} from "../../service/image.service";
import {CardImageComponent} from "../card-image/card-image.component";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {CardDirectorComponent} from "../card-director/card-director.component";
import {Director} from "../../types/Director";
import {DirectorService} from "../../service/director.service";
import {Button} from "primeng/button";
import {TranslatePipe} from "../../pipes/translate.pipe";
import {DialogModule} from "primeng/dialog";
import {ToastModule} from "primeng/toast";
import {ConfirmationService, MessageService} from "primeng/api";
import {HttpErrorResponse} from "@angular/common/http";
import {concatMap, from} from "rxjs";
import {PaginatorModule} from "primeng/paginator";
import {ConfirmDialogModule} from "primeng/confirmdialog";




@Component({
  selector: 'app-file',
  standalone: true,
  imports: [
    CardImageComponent,
    DropdownModule,
    FormsModule,
    CardDirectorComponent,
    Button,
    TranslatePipe,
    DialogModule,
    ToastModule,
    PaginatorModule,
    ConfirmDialogModule,
  ],
  templateUrl: './file.component.html',
  styleUrl: './file.component.scss',
  providers: [ConfirmationService]
})
export class FileComponent implements OnInit{
  images: ImageResponse[] = [];
  imageClick!:ImageResponse;
  directors:Director[]=[];
  selectedDir:Director|undefined;
  showNewDirector: boolean=false;
  dirName = '';
  step:boolean=false;

  //page
  first: number = 0; // Indexul primului element
  rows: number =18; // Rânduri per pagină
  totalRecords: number = 0;
  currentPage: number = 1; // Pagina curentă
  display: boolean =false;


  constructor(private confirmationService: ConfirmationService,private imageService: ImageService,private directorService:DirectorService,private messageService: MessageService) {


  }

  ngOnInit(): void {


    this.directorService.getDirectors().subscribe(data => {
      this.directors = data;
    });
  }

  clickNewDirector(): void {
    this.showNewDirector=true;
  }
  clickSelectedDirector(director: Director): void {

    this.step = true;
    this.selectedDir = director;
    this.images = [];

    this.imageService.getImages(director.id).subscribe(images => {
      this.totalRecords=images.length;
      if (images && images.length > 0) {
        const startIndex = (this.currentPage - 1) * this.rows;
        const selectedImages = images.slice(startIndex, startIndex + this.rows);

        from(selectedImages).pipe(
          concatMap((img: number) =>
            this.imageService.getImage(director.id, img,"small")
          )
        ).subscribe(imageData => {
          this.images.push(imageData);
        });
      }
    });
  }


  createDir(): void{
    this.showNewDirector=false;
    this.directorService.createDirector(this.dirName).subscribe({
      next: (data) =>{
        this.messageService.add({ severity: 'success', summary: 'Success', detail:'Your director has been create' });
        this.ngOnInit();
      },
      error:(error: HttpErrorResponse) => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail:error.error });
      }
    });
  }

  back() {
    this.step=false;
    this.first=0;
    this.currentPage=1;
  }

  onPageChange(event: any) {
    this.first = event.first;
    this.rows = event.rows;
    this.currentPage = Math.floor(this.first / this.rows) + 1;
    this.clickSelectedDirector(this.selectedDir!)
  }

  receiveMessage(event: ImageResponse) {
    this.imageClick=event;
    this.display=true;
    console.log(this.imageClick.id);
    this.imageService.getImage(this.selectedDir!.id, this.imageClick.id, "original").subscribe({
      next: (imageData) => {
        this.imageClick=imageData;
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Something went wrong', detail: error });
      }
    });
  }



  clickDelete() {
    this.imageService.deleteImage(this.imageClick.id).subscribe({
      next: (response) => {
        if(response){
          this.messageService.add({ severity: 'success', summary: 'Success', detail:'Image has been deleted' });
          this.removeItemById(this.imageClick.id);
        }
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Something went wrong', detail: error });
      }
    });
    this.display=false;
  }
  removeItemById(itemId: number): void {
    const index = this.images.findIndex(item => item.id === itemId);
    if (index !== -1) {
      this.images.splice(index, 1); // Șterge elementul găsit
    }
  }


  clickDownload() {
    const link = document.createElement('a');
    link.href = 'data:image/jpeg;base64,' + this.imageClick.base64Data;
    link.download = this.imageClick.filename || 'image.jpg'; // Numele fișierului descărcat
    link.click();
  }
  confirm() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to proceed?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {this.clickDelete()},
      reject: () => {}
    });
  }
}
