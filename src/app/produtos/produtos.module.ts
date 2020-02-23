import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { QtdeProdutoComponentModule } from '../qtde-produto/qtde-produto.module';
import { ProdutosService, NotificacaoService, StorageService, AuthService } from '../core';
import { ProdutosPage } from './produtos.page';


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
  ],
  providers: [
    ProdutosService,
    NotificacaoService,
    StorageService,
    AuthService
  ]
})
export class ProdutosPageModule { }
