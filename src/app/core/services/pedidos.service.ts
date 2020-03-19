import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { getInputDateValue } from '../../shared/utils/form-utils';
import { ResponsePedidoAdapter } from '../adapters/response-pedido.adapter';
import { ObterPedidoDto } from '../domain/obter-pedido.dto';
import { initializePedido, Pedido, PedidoStatus } from '../domain/pedido';
import { Produto } from '../domain/produto';
import { PedidoStore } from '../store/pedido.store';
import { ApiService } from './api.service';
import { MENSAGENS } from '../../shared/mensagens/mensagens';
import { CarrinhoStore } from '../store/carrinho.store';
import { ProdutosStore } from '../store/produtos.store';

@Injectable({
  providedIn: 'root',
})
export class PedidosService {

  constructor(
    private apiService: ApiService,
    private pedidoStore: PedidoStore,
    private carrinhoStore: CarrinhoStore,
    private produtosStore: ProdutosStore,
    private responsePedidoAdapter: ResponsePedidoAdapter) {
  }

  cadastrarNovoPedidoForm(formData: any) {
    const pedido = this.montarPedidoParaCadastro(formData);
    if (this.isObservacaoInvalida(pedido.observacao)) {
      throw new Error(MENSAGENS.VALIDACAO_OBS);
    }
    this.cadastrarNovoPedido(pedido);
  }

  cadastrarNovoPedido(pedido: Pedido) {
    this.pedidoStore.setStatusEnviandoCriar();
    this.apiService.post<Pedido>(`/pedidos`, pedido).subscribe(
      data => {
        this.carrinhoStore.limparCarrinho();
        this.produtosStore.restoreProdutos();
        this.pedidoStore.setStatusEnviadoCriar(MENSAGENS.CRIACAO_NOVO_PEDIDO(data));
      },
      error => {
        this.pedidoStore.setStatusNaoEnviadoCriar();
        throw error;
      }
    );
  }

  obterPedidoStatusCriar(): Observable<PedidoStatus> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.statusCriar)
      );
  }

  setPedidoStatusNaoEnviadoCriar() {
    this.pedidoStore.setStatusNaoEnviadoCriar();
  }

  atualizarPedidoForm(formData: any) {
    const pedido = this.montarPedido(formData);
    if (this.isObservacaoInvalida(pedido.observacao)) {
      throw new Error(MENSAGENS.VALIDACAO_OBS);
    }
    this.atualizarPedido(pedido);
  }

  atualizarPedido(pedido: Pedido) {
    this.pedidoStore.setStatusEnviandoAlterar();
    this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}`, pedido)
      .subscribe(
        data => {
          const pedidoAlterado = this.responsePedidoAdapter.adapt(data);
          this.pedidoStore.carregarPedido(pedidoAlterado);
          this.pedidoStore.carregarPedidoEdicao(pedidoAlterado);
          this.pedidoStore.setStatusEnviadoAlterar();
        },
        error => {
          this.pedidoStore.setStatusNaoEnviadoAlterar();
          throw error;
        }
      );
  }

  pesquisarPedido(obterPedidoDto: ObterPedidoDto) {
    const dataPedido = getInputDateValue(obterPedidoDto.dataPedido);
    this.apiService.get<Pedido>(`/pedidos?idCliente=${obterPedidoDto.idCliente}&dataPedido=${dataPedido}`)
      .subscribe(
        data => this.pedidoStore.carregarPedido(this.responsePedidoAdapter.adapt(data)),
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

  carregarPedidoEdicao(pedido: Pedido) {
    this.pedidoStore.carregarPedidoEdicao(_.cloneDeep(pedido));
    this.pedidoStore.setStatusCarregadoAlterar();
  }

  obterPedidoStatusAlterar(): Observable<PedidoStatus> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.statusAlterar)
      );
  }

  getPedidoEdicaoValue(): Pedido {
    return this.pedidoStore.state.pedidoEdicao;
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

  montarPedidoParaCadastro(data: any): Pedido {
    const pedido: Pedido = data;
    pedido.observacao = '';
    this.carrinhoStore.state.itens.forEach(
      item => {
        if (item.observacao) {
          pedido.observacao += `${item.produto.desProduto} : ${item.observacao}\n`;
        }
      });
    pedido.produtos = this.carrinhoStore.state.produtos;
    return pedido;
  }

  montarPedido(data: any): Pedido {
    const pedido: Pedido = data;
    pedido.produtos = this.pedidoStore.state.pedidoEdicao.produtos;
    return pedido;
  }

  isObservacaoInvalida(obs: string): boolean {
    return (obs && obs.length > 400);
  }

}
