import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClientePesquisarPage } from './cliente-pesquisar.page';
import { IonicHeaderParallaxModule } from 'ionic-header-parallax';
import { CardPedidoComponentModule } from '../../components/card-pedido/card-pedido.module';
import { CardClienteComponentModule } from 'src/app/components/card-cliente/card-cliente.module';
import { CardClienteComponent } from 'src/app/components/card-cliente/card-cliente.component';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicHeaderParallaxModule,
    CardPedidoComponentModule,
    CardClienteComponentModule,
    RouterModule.forChild([{ path: '', component: ClientePesquisarPage }])
  ],
  declarations: [
    ClientePesquisarPage
  ],
  entryComponents: [
    CardClienteComponent
  ]
})
export class ClientePesquisarPageModule {}
