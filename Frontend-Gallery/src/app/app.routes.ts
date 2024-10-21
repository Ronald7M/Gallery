import {Routes} from '@angular/router';
import {LoginComponent} from "../login/login.component";
import {UploderComponent} from "../upload_file/uploader/uploder.component";
import {FileComponent} from "../view_file/file/file.component";
import {authGuard} from "../guard/guards/auth.guard";

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {path: 'login', component: LoginComponent},
  {path: 'upload', component: UploderComponent, canActivate:[authGuard]},
  {path: 'view', component: FileComponent ,canActivate:[authGuard]}
];
