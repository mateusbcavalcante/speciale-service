import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PedidosPage } from './pedidos.page';
import { PageHeaderComponentModule } from '../../components/page-header/page-header.module';
import { CardPedidoComponentModule } from '../../components/card-pedido/card-pedido.module';


@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PageHeaderComponentModule,
    CardPedidoComponentModule,
    RouterModule.forChild([{ path: '', component: PedidosPage }])
  ],
  declarations: [
    PedidosPage
  ]
})
export class PedidosPageModule {}
