import { Component, OnInit, Input } from '@angular/core';
import { Pedido } from 'src/app/core/domain/pedido';

@Component({
  selector: 'app-card-pedido',
  templateUrl: 'card-pedido.component.html'
})
export class CardPedidoComponent implements OnInit {

  @Input() pedido: Pedido;

  constructor() { }

  ngOnInit() {
  }

}
