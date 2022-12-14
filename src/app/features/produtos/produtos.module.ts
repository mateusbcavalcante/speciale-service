import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProdutosPage } from './produtos.page';
import { IonicHeaderParallaxModule } from 'ionic-header-parallax';
import { CardProdutoComponentModule } from '../../components/card-produto/card-produto.module';


@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CardProdutoComponentModule,
    IonicHeaderParallaxModule,
    RouterModule.forChild([{ path: '', component: ProdutosPage }])
  ],
  declarations: [
    ProdutosPage
  ]
})
export class ProdutosPageModule { }
