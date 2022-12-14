import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PerfilPage } from './perfil.page';
import { IonicHeaderParallaxModule } from 'ionic-header-parallax';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    IonicHeaderParallaxModule,
    RouterModule.forChild([{ path: '', component: PerfilPage }])
  ],
  declarations: [
    PerfilPage
  ]
})
export class PerfilPageModule {}
