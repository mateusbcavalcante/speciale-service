import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ContadorItemComponentModule } from '../contador-item/contador-item.module';
import { ProdutosPedidoComponent } from './produtos-pedido.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ContadorItemComponentModule
  ],
  declarations: [
    ProdutosPedidoComponent
  ],
  exports: [
    ProdutosPedidoComponent
  ]
})
export class ProdutosPedidoComponentModule { }
