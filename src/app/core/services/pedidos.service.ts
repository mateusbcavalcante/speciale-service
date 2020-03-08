import { Injectable } from '@angular/core';
import { Observable, of, BehaviorSubject, throwError } from 'rxjs';
import { ApiService } from './api.service';
import { Pedido } from '../domain/pedido';
import { ObterPedidoDto } from '../domain/obter-pedido.dto';
import { formatDateParam } from '../../shared/utils/date-utils';
import { Item } from '../domain/item';
import { Produto } from '../domain/produto';
import * as _ from 'lodash';


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

  private updateDataService(pedido?: Pedido) {
    if (pedido) {
      this._store.pedido = pedido;
    }
    this._pedido.next(Object.assign({}, this._store).pedido);
  }

  private templateObservacaoItem(item: Item): string {
    return `#-------------------------\n
    ${item.produto.desProduto}\n
    ${item.observacao}\n`;
  }

  private montarPedidoPorItens(pedido: Pedido, itens: Item[]): Pedido {
    const produtos = [];
    let observacao = '';
    itens.forEach(item => {
      if (item.observacao) {
        observacao += this.templateObservacaoItem(item);
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

  removerProdutoPedido(produto: Produto) {
    _.remove(this._store.pedido.produtos, { idProduto: produto.idProduto });
    produto.status = 'DISPONIVEL';
    this.updateDataService();
  }

  obterPedidoPorId(idPedido: number) {
    if (this._store.pedido.idPedido === idPedido) {
      this.updateDataService();
    } else {
      throw new Error('Pedido está desatualizado. Favor pesquisar novamente');
    }
  }

  getPedido(): Pedido {
    return this._store.pedido;
  }

}
