import { Injectable } from '@angular/core';
import { Cliente, ClienteState } from '../domain/cliente';
import { Store } from '../store/store';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root',
})
export class ClienteStore extends Store<ClienteState> {

  constructor() {
    super(new ClienteState());
  }

  carregarCliente(cliente: Cliente) {
    this.setCliente(cliente);
  }

  private setCliente(cliente: Cliente) {
    this.setState({
      ...this.state,
      cliente: cliente
    });
  }
}
