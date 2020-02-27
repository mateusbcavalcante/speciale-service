import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProdutosPage } from './produtos.page';
import { ListaProdutosComponentModule } from '../../components/lista-produtos/lista-produtos.module';


@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ListaProdutosComponentModule,
    RouterModule.forChild([{ path: '', component: ProdutosPage }])
  ],
  declarations: [
    ProdutosPage
  ]
})
export class ProdutosPageModule { }
