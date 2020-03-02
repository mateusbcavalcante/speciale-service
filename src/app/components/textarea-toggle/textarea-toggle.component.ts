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

  @Input()
  item: Item;

  visible = false;

  textareaValue = '';

  onTouch: any = () => { };

  constructor() { }

  set value(val: string) {
    this.textareaValue = val;
    this.onChange(val);
    this.onTouch(val);
  }

  writeValue(obj: any): void {
    this.value = obj;
  }

  onChange(event: any) {
    this.textareaValue = event.target.value;
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

  save() {
    this.item.observacao = this.textareaValue;
  }

}
