import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { CardPedidoComponent } from './card-pedido.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ],
  declarations: [
    CardPedidoComponent
  ],
  exports: [
    CardPedidoComponent
  ]
})
export class CardPedidoComponentModule { }
