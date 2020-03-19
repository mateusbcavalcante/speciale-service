import { Injectable } from '@angular/core';
import { CarrinhoState } from '../domain/carrinho';
import { Item } from '../domain/item';
import { Produto } from '../domain/produto';
import { Store } from '../store/store';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root',
})
export class CarrinhoStore extends Store<CarrinhoState> {

  constructor() {
    super(new CarrinhoState());
  }

  addProdutoCarrinho(produto: Produto) {
    this.setState({
      ...this.state,
      itens: [...this.state.itens, { produto }],
      produtos: [...this.state.produtos, produto],
    });
  }

  removerItemCarrinho(item: Item) {
    this.setState({
      ...this.state,
      itens: [...this.filtrarItensNaoRemovidos(item)],
      produtos: [...this.filtrarProdutosNaoRemovidos(item.produto)]
    });
  }

  limparCarrinho(): void {
    this.setState({
      ...this.state,
      itens: [],
      produtos: []
    });
  }

  private filtrarItensNaoRemovidos(item: Item): Item[] {
    _.remove(this.state.itens, { produto: { idProduto: item.produto.idProduto } });
    return this.state.itens;
  }

  private filtrarProdutosNaoRemovidos(produto: Produto): Produto[] {
    _.remove(this.state.produtos, { idProduto: produto.idProduto });
    return this.state.produtos;
  }
}
