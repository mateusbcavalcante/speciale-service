import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CarrinhoPage } from './carrinho.page';
import { ListaProdutosComponentModule } from '../../components/lista-produtos/lista-produtos.module';
import { PageHeaderComponentModule } from 'src/app/components/page-header/page-header.module';



@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ListaProdutosComponentModule,
    PageHeaderComponentModule,
    RouterModule.forChild([{ path: '', component: CarrinhoPage }])
  ],
  declarations: [
    CarrinhoPage
  ]
})
export class CarrinhoPageModule {}
