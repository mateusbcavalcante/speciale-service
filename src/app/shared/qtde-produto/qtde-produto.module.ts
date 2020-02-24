import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { QtdeProdutoComponent } from './qtde-produto.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ],
  declarations: [
    QtdeProdutoComponent
  ],
  exports: [
    QtdeProdutoComponent
  ]
})
export class QtdeProdutoComponentModule { }
