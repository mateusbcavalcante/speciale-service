import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { TextareaToggleComponent } from './textarea-toggle.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ],
  declarations: [
    TextareaToggleComponent
  ],
  exports: [
    TextareaToggleComponent
  ]
})
export class TextareaToggleComponentModule { }
