import { Injectable } from '@angular/core';
import { Produto, ProdutosState } from '../domain/produto';
import { Store } from './store';
import { CarrinhoStore } from './carrinho.store';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root',
})
export class ProdutosStore extends Store<ProdutosState> {

  constructor(
    private carrinhoStore: CarrinhoStore
  ) {
    super(new ProdutosState());
  }

  carregarProdutos(produtos: Produto[]) {
    this.setProdutos(this.filtrarProdutosCarrinho(produtos));
  }

  addProduto(produto: Produto) {
    this.setState({
      ...this.state,
      disponiveis: [...this.state.disponiveis, produto]
    });
    this.ordenarProdutos();
  }

  addProdutos(produtos: Produto[]) {
    this.setState({
      ...this.state,
      disponiveis: [...this.state.disponiveis, ...produtos]
    });
    this.ordenarProdutos();
  }

  restoreProdutos() {
    this.carrinhoStore.state.produtos.forEach(produto => produto.qtdSolicitada = 0);
    this.addProdutos(this.carrinhoStore.state.produtos);
  }

  removerProduto(produto: Produto) {
    _.remove(this.state.disponiveis, { idProduto: produto.idProduto });
  }

  ordenarProdutos() {
    this.setState({
      ...this.state,
      disponiveis: [..._.orderBy(this.state.disponiveis, ['desProduto'], ['asc'])]
    });
  }

  private setProdutos(produtos: Produto[]) {
    this.setState({
      ...this.state,
      disponiveis: [...produtos]
    });
  }

  private filtrarProdutosCarrinho(produtos: Produto[]): Produto[] {
    return _.pullAllBy(produtos, this.carrinhoStore.state.produtos, 'idProduto');
  }

}
