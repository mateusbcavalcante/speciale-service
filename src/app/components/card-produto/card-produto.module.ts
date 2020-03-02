import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ContadorItemComponentModule } from '../contador-item/contador-item.module';
import { CardProdutoComponent } from './card-produto.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ContadorItemComponentModule
  ],
  declarations: [
    CardProdutoComponent
  ],
  exports: [
    CardProdutoComponent
  ]
})
export class CardProdutoComponentModule { }
