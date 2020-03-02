import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CarrinhoPage } from './carrinho.page';
import { PageHeaderComponentModule } from '../../components/page-header/page-header.module';
import { CardItemComponentModule } from '../../components/card-item/card-item.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CardItemComponentModule,
    PageHeaderComponentModule,
    RouterModule.forChild([{ path: '', component: CarrinhoPage }])
  ],
  declarations: [
    CarrinhoPage
  ]
})
export class CarrinhoPageModule {}
