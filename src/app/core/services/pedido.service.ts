import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { ProdutosService } from './produtos.service';
import { Pedido } from '../domain/pedido';
import { formatDateParam } from '../utils/date-utils';
import { Produto } from '../domain/produto';

@Injectable()
export class PedidoService {

  constructor(
    private http: HttpClient,
    private produtosService: ProdutosService) {
  }

  cadastrarPedido(formData: any, produtos: Produto[]): Observable<Pedido> {
    const pedido: Pedido = formData;
    pedido.produtos = produtos;
    return this.http.post<Pedido>(`${environment.base_url}/pedidos`, pedido);
  }

  atualizarPedido(formData: any): Observable<Pedido> {
    const pedido: Pedido = formData;
    pedido.produtos = this.produtosService.getListaProdutosCarrinho();
    return this.http.put<Pedido>(`${environment.base_url}/pedidos/${pedido.idPedido}`, pedido);
  }

  obterPedidoPorParametrosPesquisa(formData: any): Observable<Pedido> {
    const pedido: Pedido = formData;
    const dataPedido = formatDateParam(pedido.dataPedido);
    return this.http.get<Pedido>(`${environment.base_url}/pedidos?idCliente=${pedido.idCliente}&dataPedido=${dataPedido}`);
  }

  inativarPedido(pedido: Pedido): Observable<Pedido> {
    return this.http.put<Pedido>(`${environment.base_url}/pedidos/${pedido.idPedido}/inativar`, pedido);
  }

}
