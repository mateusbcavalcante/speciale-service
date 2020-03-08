import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PageHeaderComponentModule } from '../../components/page-header/page-header.module';
import { ProdutosPedidoComponentModule } from '../../components/produtos-pedido/produtos-pedido.module';
import { TextareaToggleComponentModule } from '../../components/textarea-toggle/textarea-toggle.module';
import { PedidoPage } from './pedido.page';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PageHeaderComponentModule,
    ProdutosPedidoComponentModule,
    TextareaToggleComponentModule,
    RouterModule.forChild([{ path: '', component: PedidoPage }])
  ],
  declarations: [
    PedidoPage
  ]
})
export class PedidoPageModule {}
