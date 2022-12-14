import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { CardPedidoComponent } from './card-pedido.component';
import { PedidoSituacaoPipe } from './pedido-situacao.pipe';
import { PedidoOpcaoPipe } from './pedido-opcao.pipe';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ],
  declarations: [
    PedidoSituacaoPipe,
    PedidoOpcaoPipe,
    CardPedidoComponent,
  ],
  exports: [
    CardPedidoComponent
  ]
})
export class CardPedidoComponentModule { }
