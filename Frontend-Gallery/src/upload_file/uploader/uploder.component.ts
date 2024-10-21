import {Component, OnInit} from '@angular/core';
import {ToastModule} from "primeng/toast";
import {FileUploadErrorEvent, FileUploadEvent, FileUploadModule} from "primeng/fileupload";
import {MessageService} from "primeng/api";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {TranslatePipe} from "../../pipes/translate.pipe";
import {environment} from "../../environments/environment";
import {Director} from "../../types/Director";
import {DirectorService} from "../../service/director.service";
import {NgClass} from "@angular/common";

interface UploadEvent {
  originalEvent: Event;
  files: File[];
}

@Component({
  selector: 'app-uploder',
  standalone: true,
  imports: [
    ToastModule,
    FileUploadModule,
    DropdownModule,
    FormsModule,
    TranslatePipe,
    NgClass
  ],
  templateUrl: './uploder.component.html',
  styleUrl: './uploder.component.scss'
})
export class UploderComponent implements OnInit {
  uploadedFiles: any[] = [];
  errorMessage: string | null = null

  files: Director[] |undefined;
  selectedDirector: Director| undefined;


  constructor(private messageService: MessageService,private directorService:DirectorService) {


  }
  ngOnInit() {
    this.getDirectors()
    console.log(this.files)
  }

  onUpload(event: FileUploadEvent) {
    for(let file of event.files) {
      this.uploadedFiles.push(file);
      console.log(file);
    }

    this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: ''});
  }

  onError($event: FileUploadErrorEvent) {
    this.messageService.add({
      severity: 'error',
      summary: 'File Upload Error',
      detail: `Error: ${$event.error!.message || 'Unknown error'}` // Aici poți accesa detalii despre eroare
    });
  }

  onSelect(event: FileUploadErrorEvent): void {
    const selectedFiles = event.files;

    if (selectedFiles.length > 50) {
      this.errorMessage = 'You cannot upload more than 50 files.';
      this.messageService.add({severity: 'error', summary: 'File Limit Exceeded', detail: this.errorMessage});

      // Remove extra files from the list (optional behavior)
      event.files.splice(50);

    } else {
      this.errorMessage = null;
    }
  }

  getUrl(): string{
    var url:string=environment.apiUrl+"/upload/"+this.selectedDirector?.id;
    return url;
  }

  getDirectors(): void{
    this.directorService.getDirectors().subscribe(data => {
      this.files= data;
    });
  }
}
