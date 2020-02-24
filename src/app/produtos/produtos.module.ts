import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProdutosPage } from './produtos.page';
import { QtdeProdutoComponentModule } from '../shared/qtde-produto/qtde-produto.module';


@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    QtdeProdutoComponentModule,
    RouterModule.forChild([{ path: '', component: ProdutosPage }])
  ],
  declarations: [
    ProdutosPage
  ]
})
export class ProdutosPageModule { }
