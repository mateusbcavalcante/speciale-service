import { Component, OnInit, Input } from '@angular/core';
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

  constructor() { }

  ngOnInit() {
  }

}
