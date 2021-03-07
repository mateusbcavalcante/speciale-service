import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Cliente } from 'src/app/core/domain/cliente';

@Component({
  selector: 'app-card-cliente',
  templateUrl: 'card-cliente.component.html',
  styleUrls: [
    'card-cliente.component.scss'
  ]
})
export class CardClienteComponent implements OnInit {

  @Input()
  cliente: Cliente;

  @Output()
  selecionarCliente: EventEmitter<any> = new EventEmitter<any>();

  constructor() { 
  }

  ngOnInit() {
  }

  selecionarClienteEvent(cliente: Cliente) {
    this.selecionarCliente.emit({
      cliente
    });
  }
}
