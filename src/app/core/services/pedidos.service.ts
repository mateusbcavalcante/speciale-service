import { Injectable } from '@angular/core';
import { Observable, of, BehaviorSubject, throwError } from 'rxjs';
import { ApiService } from './api.service';
import { Pedido } from '../domain/pedido';
import { ObterPedidoDto } from '../domain/obter-pedido.dto';
import { formatDateParam } from '../../shared/utils/date-utils';
import { Item } from '../domain/item';
import { catchError } from 'rxjs/operators';


const initializePedido = (): Pedido => {
  return {
    idCliente: 0,
    idUsuario: 0,
    idPedido: 0,
    dataPedido: null,
    observacao: '',
    flgAtivo: '',
    produtos: [],
    usuarioCadastro: null
  };
};

@Injectable({
  providedIn: 'root',
})
export class PedidosService {

  // tslint:disable-next-line: variable-name
  private _pedido = new BehaviorSubject<Pedido>(initializePedido());
  // tslint:disable-next-line: variable-name
  private _store: { pedido: Pedido } = { pedido: initializePedido() };
  readonly pedido = this._pedido.asObservable();

  constructor(
    private apiService: ApiService) {
  }

  private updateDataService(pedido: Pedido) {
    this._store.pedido = pedido;
    this._pedido.next(Object.assign({}, this._store).pedido);
  }

  private montarPedidoPorItens(pedido: Pedido, itens: Item[]): Pedido {
    const produtos = [];
    let observacao = '';
    itens.forEach(item => {
      if (item.observacao) {
        observacao += `${item.produto.desProduto}\n${item.observacao}\n`;
      }
      produtos.push(item.produto);
    });
    pedido.produtos = produtos;
    pedido.observacao = observacao;
    return pedido;
  }

  cadastrarNovoPedido(pedido: Pedido, itens: Item[]): Observable<Pedido> {
    pedido = this.montarPedidoPorItens(pedido, itens);
    return this.apiService.post<Pedido>(`/pedidos`, pedido);
  }

  atualizarPedido(pedido: Pedido) {
    this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}`, pedido).subscribe(
      data => this.updateDataService(data)
    );
  }

  obterPedido(obterPedidoDto: ObterPedidoDto) {
    const dataPedido = formatDateParam(obterPedidoDto.dataPedido);
    this.apiService.get<Pedido>(`/pedidos?idCliente=${obterPedidoDto.idCliente}&dataPedido=${dataPedido}`)
      .subscribe(
        data => this.updateDataService(data),
        error => {
          this.updateDataService(initializePedido());
          throw error;
        }
      );
  }

  inativarPedido(pedido: Pedido) {
    this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}/inativar`, pedido).subscribe(
      data => this.updateDataService(data)
    );
  }

  getPedido(): Pedido {
    return this._store.pedido;
  }

}
