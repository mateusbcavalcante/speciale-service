import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ContadorItemComponent } from './contador-item.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ],
  declarations: [
    ContadorItemComponent
  ],
  exports: [
    ContadorItemComponent
  ]
})
export class ContadorItemComponentModule { }
