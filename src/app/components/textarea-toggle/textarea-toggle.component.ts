import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Item } from '../../core/domain/item';

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

  // tslint:disable-next-line: no-input-rename
  @Input('value')
  textareaValue: string;

  visible = false;

  onTouch: any = () => { };

  onChange: any = () => { };

  constructor() { }

  set value(val: string) {
    this.textareaValue = val;
    this.onChange(val);
    this.onTouch(val);
  }

  writeValue(obj: any): void {
    this.value = obj;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
  }

  toggle(event: any) {
    this.visible = !this.visible;
  }

}
