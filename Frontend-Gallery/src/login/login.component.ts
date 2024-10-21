import {Component, EventEmitter, inject, Inject, OnInit, Output} from '@angular/core';
import {FloatLabelModule} from "primeng/floatlabel";
import {Button} from "primeng/button";
import {TranslatePipe} from "../pipes/translate.pipe";
import { ReactiveFormsModule,FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Credential} from "../types/Credential";
import {LoginService} from "../service/login.service";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FloatLabelModule,
    Button,
    TranslatePipe,
    ReactiveFormsModule,
    ToastModule,

  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',

})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  @Output() loginSuccess = new EventEmitter<boolean>();


  constructor(private loginService: LoginService, private fb: FormBuilder,private messageService: MessageService,private router:Router) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {

  }
  bibi(){
    this.messageService.add({ severity: 'success', summary: 'Doar adevarul 45653', detail: 'Te iubesc bibica meaaaa' });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const userCredential: Credential = {
        email: this.loginForm.get("username")?.value,
        password: this.loginForm.get("password")?.value
      };
      this.loginService.login(userCredential).subscribe({
        next: (response) => {
          localStorage.setItem("token",response);
          this.messageService.add({ severity: 'success', summary: 'Success', detail:'Connection successfully' });
          setTimeout(() => {
            window.location.reload();

          }, 1500);


        },
        error: (err) => {
          localStorage.setItem("token",'');
          this.messageService.add({ severity: 'error', summary: 'Invalid', detail:err.error });
          this.loginForm.get('username')?.setValue('');
          this.loginForm.get('password')?.setValue('');
        }
      });
    }

  }
}



