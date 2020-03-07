import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Produto } from '../../core/domain/produto';

@Component({
  selector: 'app-card-produto',
  templateUrl: 'card-produto.component.html',
  styleUrls: [
    'card-produto.component.scss'
  ]
})
export class CardProdutoComponent implements OnInit {

  @Input()
  produto: Produto;

  @Output()
  addItem: EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  ngOnInit() {
  }

  addItemEvent(produto: Produto) {
    this.addItem.emit({
      produto
    });
  }

}
