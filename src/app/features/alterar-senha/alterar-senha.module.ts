import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PageHeaderComponentModule } from '../../components/page-header/page-header.module';
import { AlterarSenhaPage } from './alterar-senha.page';


@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PageHeaderComponentModule,
    RouterModule.forChild([{ path: '', component: AlterarSenhaPage }])
  ],
  declarations: [
    AlterarSenhaPage
  ]
})
export class AlterarSenhaPageModule {}
