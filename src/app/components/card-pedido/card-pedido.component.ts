import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Pedido } from '../../core/domain/pedido';

@Component({
  selector: 'app-card-pedido',
  templateUrl: 'card-pedido.component.html',
  styleUrls: [
    'card-pedido.component.scss'
  ]
})
export class CardPedidoComponent implements OnInit {

  @Input() pedido: Pedido;

  @Output()
  inativar: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  editar: EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  ngOnInit() {
  }

  inativarEvent(pedido: Pedido) {
    this.inativar.emit({
      pedido
    });
  }

  editarEvent(pedido: Pedido) {
    this.editar.emit({
      pedido
    });
  }

}
