import { Injectable } from '@angular/core';
import { Pedido, PedidoState, PedidoStatus, PedidoTipoStatus } from '../domain/pedido';
import { Store } from '../store/store';
import * as _ from 'lodash';
import { Produto } from '../domain/produto';

@Injectable({
  providedIn: 'root',
})
export class PedidoStore extends Store<PedidoState> {

  constructor() {
    super(new PedidoState());
  }

  carregarPedido(pedido: Pedido) {
    this.setPedido(pedido);
  }

  carregarPedidoEdicao(pedido: Pedido) {
    this.setPedidoEdicao(pedido);
  }

  carregarProdutos(produtos: Produto[]) {
    this.setProdutos(produtos);
  }

  addProduto(produto: Produto) {
    this.setState({
      ...this.state,
      produtos: [...this.state.produtos, produto]
    });
    this.ordenarProdutos();
  }

  removerProduto(produto: Produto) {
    _.remove(this.state.produtos, { idProduto: produto.idProduto });
    this.setProdutos(this.state.produtos);
  }

  removerProdutoPedido(produto: Produto) {
    _.remove(this.state.pedidoEdicao.produtos, { idProduto: produto.idProduto });
    this.setPedidoEdicao(this.state.pedidoEdicao);
  }

  addProdutoPedido(produto: Produto) {
    this.state.pedidoEdicao.produtos.push(produto);
    this.ordenarProdutosPedido();
    // this.setPedidoEdicao(this.state.pedidoEdicao);
  }

  ordenarProdutos() {
    this.setState({
      ...this.state,
      produtos: [..._.orderBy(this.state.produtos, ['desProduto'], ['asc'])]
    });
  }

  ordenarProdutosPedido() {
    _.orderBy(this.state.pedidoEdicao.produtos, ['desProduto'], ['asc']);
  }

  setStatusEnviandoCriar(mensagem = '') {
    this.setStatusCriar(new PedidoStatus(PedidoTipoStatus.ENVIANDO, mensagem));
  }

  setStatusEnviadoCriar(mensagem = '') {
    this.setStatusCriar(new PedidoStatus(PedidoTipoStatus.ENVIADO, mensagem));
  }

  setStatusNaoEnviadoCriar(mensagem = '') {
    this.setStatusCriar(new PedidoStatus(PedidoTipoStatus.NAO_ENVIADO, mensagem));
  }

  setStatusEnviandoAlterar(mensagem = '') {
    this.setStatusAlterar(new PedidoStatus(PedidoTipoStatus.ENVIANDO, mensagem));
  }

  setStatusEnviadoAlterar(mensagem = '') {
    this.setStatusAlterar(new PedidoStatus(PedidoTipoStatus.ENVIADO, mensagem));
  }

  setStatusNaoEnviadoAlterar(mensagem = '') {
    this.setStatusAlterar(new PedidoStatus(PedidoTipoStatus.NAO_ENVIADO, mensagem));
  }

  setStatusCarregadoAlterar(mensagem = '') {
    this.setStatusAlterar(new PedidoStatus(PedidoTipoStatus.CARREGADO, mensagem));
  }

  setStatusNaoCarregadoAlterar(mensagem = '') {
    this.setStatusAlterar(new PedidoStatus(PedidoTipoStatus.NAO_CARREGADO, mensagem));
  }

  private setStatusCriar(pedidoStatus: PedidoStatus) {
    this.setState({
      ...this.state,
      statusCriar: pedidoStatus
    });
  }

  private setStatusAlterar(pedidoStatus: PedidoStatus) {
    this.setState({
      ...this.state,
      statusAlterar: pedidoStatus
    });
  }

  private setPedido(novoPedido: Pedido) {
    this.setState({
      ...this.state,
      pedido: novoPedido
    });
  }

  private setPedidoEdicao(novoPedido: Pedido) {
    this.setState({
      ...this.state,
      pedidoEdicao: novoPedido
    });
  }

  private setProdutos(produtosDisponiveis: Produto[]) {
    this.setState({
      ...this.state,
      produtos: [...this.filtrarProdutosPedido(produtosDisponiveis)]
    });
  }

  private filtrarProdutosPedido(produtos: Produto[]): Produto[] {
    const produtosPedido = this.state.pedidoEdicao.produtos.filter(p => p.flgAtivo !== 'N');
    return _.pullAllBy(produtos, produtosPedido, 'idProduto');
  }
}
