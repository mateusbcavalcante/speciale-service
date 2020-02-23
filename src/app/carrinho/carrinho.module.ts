import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProdutosService, StorageService, NotificacaoService, AuthService, PedidoService, AlertService } from '../core';
import { QtdeProdutoComponentModule } from '../qtde-produto/qtde-produto.module';
import { CarrinhoPage } from './carrinho.page';


@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    QtdeProdutoComponentModule,
    RouterModule.forChild([{ path: '', component: CarrinhoPage }])
  ],
  declarations: [
    CarrinhoPage
  ],
  providers: [
    ProdutosService,
    StorageService,
    NotificacaoService,
    AuthService,
    PedidoService,
    AlertService
  ]
})
export class CarrinhoPageModule {}
