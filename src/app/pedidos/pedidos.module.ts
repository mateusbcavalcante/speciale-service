import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NotificacaoService, AuthService, PedidoService, ProdutosService, StorageService, AlertService } from '../core';
import { PedidosPage } from './pedidos.page';



@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild([{ path: '', component: PedidosPage }])
  ],
  declarations: [
    PedidosPage
  ],
  providers: [
    NotificacaoService,
    AuthService,
    PedidoService,
    ProdutosService,
    StorageService,
    AlertService
  ]
})
export class PedidosPageModule {}
