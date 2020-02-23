import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, StorageService } from '../core';
import { PerfilPage } from './perfil.page';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    RouterModule.forChild([{ path: '', component: PerfilPage }])
  ],
  declarations: [
    PerfilPage
  ],
  providers: [
    AuthService,
    StorageService
  ]
})
export class PerfilPageModule {}
