import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ProdutosService } from './produtos.service';
import { Pedido } from '../domain/pedido';
import { ObterPedidoDto } from '../domain/obter-pedido.dto';
import { formatDateParam } from '../../shared/utils/date-utils';


@Injectable({
  providedIn: 'root',
})
export class PedidosService {

  constructor(
    private apiService: ApiService,
    private produtosService: ProdutosService) {
  }

  cadastrarPedido(pedido: Pedido): Observable<Pedido> {
    return this.apiService.post<Pedido>(`/pedidos`, pedido);
  }

  atualizarPedido(pedido: Pedido): Observable<Pedido> {
    pedido.produtos = this.produtosService.getListaProdutosCarrinho();
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
