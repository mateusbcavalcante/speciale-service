import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { LoginPage } from './login.page';
import { EsqueceuSenhaPage } from '../esqueceu-senha/esqueceu-senha.page';

const routes: Routes = [
  {
    path: '',
    component: LoginPage
  }
];

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    LoginPage,
    EsqueceuSenhaPage
  ],
  entryComponents: [
    EsqueceuSenhaPage
  ]
})
export class LoginPageModule { }
