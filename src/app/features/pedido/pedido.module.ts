import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { IonicHeaderParallaxModule } from 'ionic-header-parallax';
import { DataEntregaComponentModule } from '../../components/data-entrega/data-entrega.module';
import { ListaProdutosComponent } from '../../components/lista-produtos/lista-produtos.component';
import { ListaProdutosComponentModule } from '../../components/lista-produtos/lista-produtos.module';
import { ProdutosPedidoComponentModule } from '../../components/produtos-pedido/produtos-pedido.module';
import { TextareaToggleComponentModule } from '../../components/textarea-toggle/textarea-toggle.module';
import { PedidoPage } from './pedido.page';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicHeaderParallaxModule,
    ProdutosPedidoComponentModule,
    TextareaToggleComponentModule,
    ListaProdutosComponentModule,
    DataEntregaComponentModule,
    RouterModule.forChild([{ path: '', component: PedidoPage }])
  ],
  declarations: [
    PedidoPage
  ],
  entryComponents: [
    ListaProdutosComponent
  ]
})
export class PedidoPageModule {}
