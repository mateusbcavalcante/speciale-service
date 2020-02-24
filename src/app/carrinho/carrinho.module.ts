import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CarrinhoPage } from './carrinho.page';
import { QtdeProdutoComponentModule } from '../shared/qtde-produto/qtde-produto.module';


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
  ]
})
export class CarrinhoPageModule {}
