import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ProdutosService } from './produtos.service';
import { Pedido, Produto } from '../../domain';
import { formatDateParam } from '../../utils';


@Injectable({
  providedIn: 'root',
})
export class PedidoService {

  constructor(
    private apiService: ApiService,
    private produtosService: ProdutosService) {
  }

  cadastrarPedido(formData: any, produtos: Produto[]): Observable<Pedido> {
    const pedido: Pedido = formData;
    pedido.produtos = produtos;
    return this.apiService.post<Pedido>(`/pedidos`, pedido);
  }

  atualizarPedido(formData: any): Observable<Pedido> {
    const pedido: Pedido = formData;
    pedido.produtos = this.produtosService.getListaProdutosCarrinho();
    return this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}`, pedido);
  }

  obterPedidoPorParametrosPesquisa(formData: any): Observable<Pedido> {
    const pedido: Pedido = formData;
    const dataPedido = formatDateParam(pedido.dataPedido);
    return this.apiService.get<Pedido>(`/pedidos?idCliente=${pedido.idCliente}&dataPedido=${dataPedido}`);
  }

  inativarPedido(pedido: Pedido): Observable<Pedido> {
    return this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}/inativar`, pedido);
  }

}
