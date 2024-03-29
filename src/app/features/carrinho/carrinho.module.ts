import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CarrinhoPage } from './carrinho.page';
import { IonicHeaderParallaxModule } from 'ionic-header-parallax';
import { CardItemComponentModule } from '../../components/card-item/card-item.module';
import { DataEntregaComponentModule } from '../../components/data-entrega/data-entrega.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CardItemComponentModule,
    IonicHeaderParallaxModule,
    DataEntregaComponentModule,
    RouterModule.forChild([{ path: '', component: CarrinhoPage }])
  ],
  declarations: [
    CarrinhoPage
  ]
})
export class CarrinhoPageModule {}
