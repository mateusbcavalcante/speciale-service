import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ListaProdutosComponent } from './lista-produtos.component';
import { CardProdutoComponentModule } from '../card-produto/card-produto.module';
import { PageHeaderComponentModule } from '../page-header/page-header.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CardProdutoComponentModule,
    PageHeaderComponentModule
  ],
  declarations: [
    ListaProdutosComponent
  ],
  exports: [
    ListaProdutosComponent
  ]
})
export class ListaProdutosComponentModule { }
