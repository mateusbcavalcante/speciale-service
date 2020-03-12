import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { getInputDateValue } from '../../shared/utils/form-utils';
import { ObterPedidoDto } from '../domain/obter-pedido.dto';
import { initializePedido, Pedido } from '../domain/pedido';
import { Produto } from '../domain/produto';
import { PedidoStore } from '../store/pedido.store';
import { ApiService } from './api.service';


@Injectable({
  providedIn: 'root',
})
export class PedidosService {

  constructor(
    private apiService: ApiService,
    private pedidoStore: PedidoStore) {
  }

  cadastrarNovoPedido(pedido: Pedido): Observable<Pedido> {
    return this.apiService.post<Pedido>(`/pedidos`, pedido);
  }

  atualizarPedido(pedido: Pedido) {
    this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}`, pedido)
      .subscribe(data => this.pedidoStore.carregarPedido(data));
  }

  pesquisarPedido(obterPedidoDto: ObterPedidoDto) {
    const dataPedido = getInputDateValue(obterPedidoDto.dataPedido);
    this.apiService.get<Pedido>(`/pedidos?idCliente=${obterPedidoDto.idCliente}&dataPedido=${dataPedido}`)
      .subscribe(
        data => this.pedidoStore.carregarPedido(data),
        error => {
          this.pedidoStore.carregarPedido(initializePedido());
          throw error;
        }
      );
  }

  inativarPedido(pedido: Pedido) {
    this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}/inativar`, pedido)
      .subscribe(data => this.pedidoStore.carregarPedido(initializePedido()));
  }

  removerProdutoPedido(produto: Produto) {
    produto.qtdSolicitada = 0;
    if (produto.idPedidoProduto) {
      produto.flgAtivo = 'N';
    } else {
      this.pedidoStore.removerProdutoPedido(produto);
    }
    this.pedidoStore.addProduto(produto);
  }

  addProdutoPedido(produto: Produto) {
    this.pedidoStore.addProdutoPedido(produto);
    this.pedidoStore.removerProduto(produto);
  }

  obterPedido(): Observable<Pedido> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.pedido)
      );
  }

  obterPedidoEdicao(): Observable<Pedido> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.pedidoEdicao)
      );
  }

  obterProdutos(): Observable<Produto[]> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.produtos)
      );
  }

  listarProdutosPorCliente(idCliente?: number) {
    this.apiService.get<Produto[]>(`/produtos/clientes/${idCliente}`)
      .subscribe(
        (produtos: Produto[]) => this.pedidoStore.carregarProdutos(produtos)
      );
  }

  montarPedido(data: any): Pedido {
    const pedido: Pedido = data;
    pedido.produtos = this.pedidoStore.state.pedidoEdicao.produtos;
    return pedido;
  }

}
