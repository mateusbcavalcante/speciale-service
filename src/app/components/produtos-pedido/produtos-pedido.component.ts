import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Produto } from '../../core/domain/produto';
import { Pedido } from '../../core/domain/pedido';

@Component({
  selector: 'app-produtos-pedido',
  templateUrl: 'produtos-pedido.component.html',
  styleUrls: [
    'produtos-pedido.component.scss'
  ]
})
export class ProdutosPedidoComponent implements OnInit {

  @Input()
  pedido: Pedido;

  @Output()
  removerProduto: EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  ngOnInit() {
  }

  // addItemEvent(produto: Produto) {
  //   this.addItem.emit({
  //     produto
  //   });
  // }

  removerProdutoEvent(produto: Produto) {
    this.removerProduto.emit({
      produto
    });
  }

}
