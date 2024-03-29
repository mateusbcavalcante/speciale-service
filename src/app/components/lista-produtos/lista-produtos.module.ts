import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { IonicHeaderParallaxModule } from 'ionic-header-parallax';
import { ListaProdutosComponent } from './lista-produtos.component';
import { CardProdutoComponentModule } from '../card-produto/card-produto.module';
import { PageHeaderComponentModule } from '../page-header/page-header.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    IonicHeaderParallaxModule,
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
