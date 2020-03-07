import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PerfilPage } from './perfil.page';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    RouterModule.forChild([{ path: '', component: PerfilPage }])
  ],
  declarations: [
    PerfilPage
  ]
})
export class PerfilPageModule {}
