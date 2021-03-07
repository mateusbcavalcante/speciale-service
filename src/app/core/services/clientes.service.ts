import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from './api.service';
import { Cliente, initializeCliente } from '../domain/cliente';
import { ObterClienteDto } from '../domain/obter-cliente.dto';
import { ClienteStore } from '../store/cliente.store';

@Injectable({
  providedIn: 'root',
})
export class ClientesService {

  constructor(
    private apiService: ApiService,
    private clienteStore: ClienteStore) {
  }

  setCliente(cliente: Cliente) {
    this.clienteStore.carregarCliente(cliente);
  }

  obterCliente(): Observable<Cliente> {
    return this.clienteStore.state$
      .pipe(
        map(clienteState => clienteState.cliente)
      );
  }

  resetarCliente() {
    this.clienteStore.carregarCliente(initializeCliente());
  }

  isFiltroPesquisaValido(obterPedidoDto: ObterClienteDto): boolean {
    return !!obterPedidoDto.nomeCliente && obterPedidoDto.nomeCliente.length >= 3;
  }

  pesquisarClientes(obterClienteDto?: ObterClienteDto): Observable<Cliente[]> {
    const nomeCliente = !!obterClienteDto.nomeCliente ? obterClienteDto.nomeCliente : "";
    return this.apiService.get<Cliente[]>(`/clientes/${nomeCliente}`);
  }
}
