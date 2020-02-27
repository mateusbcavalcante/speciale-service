import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ListaProdutosComponent } from './lista-produtos.component';
import { ContadorProdutoComponentModule } from '../contador-produto/contador-produto.module';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ContadorProdutoComponentModule
  ],
  declarations: [
    ListaProdutosComponent
  ],
  exports: [
    ListaProdutosComponent
  ]
})
export class ListaProdutosComponentModule { }