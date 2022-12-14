import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { TextareaToggleComponentModule } from '../textarea-toggle/textarea-toggle.module';
import { ContadorItemComponentModule } from '../contador-item/contador-item.module';
import { CardItemComponent } from './card-item.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ContadorItemComponentModule,
    TextareaToggleComponentModule
  ],
  declarations: [
    CardItemComponent
  ],
  exports: [
    CardItemComponent
  ]
})
export class CardItemComponentModule { }
