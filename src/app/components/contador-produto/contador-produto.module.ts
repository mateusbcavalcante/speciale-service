import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ContadorProdutoComponent } from './contador-produto.component';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ],
  declarations: [
    ContadorProdutoComponent
  ],
  exports: [
    ContadorProdutoComponent
  ]
})
export class ContadorProdutoComponentModule { }
