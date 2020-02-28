import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-textarea-toggle',
  templateUrl: 'textarea-toggle.component.html',
  styleUrls: [
    'textarea-toggle.component.scss'
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextareaToggleComponent),
      multi: true
    }
  ]
})
export class TextareaToggleComponent implements ControlValueAccessor {

  @Input()
  label = '';

  visible = false;

  writeValue(obj: any): void {
  }

  registerOnChange(fn: any): void {
  }

  registerOnTouched(fn: any): void {
  }

  setDisabledState(isDisabled: boolean): void {
  }

  toggle(event) {
    this.visible = !this.visible;
  }

}
