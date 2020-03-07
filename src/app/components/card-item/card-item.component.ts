import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Item } from '../../core/domain/item';

@Component({
  selector: 'app-card-item',
  templateUrl: 'card-item.component.html',
  styleUrls: [
    'card-item.component.scss'
  ]
})
export class CardItemComponent implements OnInit {

  @Input() item: Item;

  @Output()
  removerItem: EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  ngOnInit() {

  }

  setObservacao(event: any) {
    this.item.observacao = event.target.value;
  }

  removerItemEvent(item: Item) {
    this.removerItem.emit({
      item
    });
  }

}
