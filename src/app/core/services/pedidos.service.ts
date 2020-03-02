import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ApiService } from './api.service';
import { Pedido } from '../domain/pedido';
import { ObterPedidoDto } from '../domain/obter-pedido.dto';
import { formatDateParam } from '../../shared/utils/date-utils';
import { Item } from '../domain/item';


@Injectable({
  providedIn: 'root',
})
export class PedidosService {

  constructor(
    private apiService: ApiService) {
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

  cadastrarPedido(pedido: Pedido, itens: Item[]): Observable<Pedido> {
    pedido = this.montarPedidoPorItens(pedido, itens);
    return this.apiService.post<Pedido>(`/pedidos`, pedido);
  }

  atualizarPedido(pedido: Pedido): Observable<Pedido> {
    // pedido.produtos = this.produtosService.getListaProdutosCarrinho();
    return this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}`, pedido);
  }

  obterPedido(obterPedidoDto: ObterPedidoDto): Observable<Pedido> {
    const dataPedido = formatDateParam(obterPedidoDto.dataPedido);
    return this.apiService.get<Pedido>(`/pedidos?idCliente=${obterPedidoDto.idCliente}&dataPedido=${dataPedido}`);
  }

  inativarPedido(pedido: Pedido): Observable<Pedido> {
    return this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}/inativar`, pedido);
  }

}
