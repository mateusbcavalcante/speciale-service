import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Item } from '../domain/item';
import { Carrinho } from '../domain/carrinho';
import * as _ from 'lodash';

const initializeCarrinho = (): Carrinho => {
  return { itens: [], totalItens: 0 };
}

@Injectable({
  providedIn: 'root',
})
export class CarrinhoService {

  // tslint:disable-next-line: variable-name
  private _carrinho = new BehaviorSubject<Carrinho>(initializeCarrinho());
  // tslint:disable-next-line: variable-name
  private _store: { carrinho: Carrinho } = { carrinho: initializeCarrinho() };
  readonly carrinho = this._carrinho.asObservable();

  private atualizarCarrinho() {
    this._store.carrinho.totalItens = this._store.carrinho.itens.length;
    this._carrinho.next(Object.assign({}, this._store).carrinho);
  }

  addItemCarrinho(item: Item) {

    const itemCarrinho = this.obterItemCarrinho(item);

    if (itemCarrinho) {
      itemCarrinho.produto.qtdSolicitada = item.produto.qtdSolicitada;
      itemCarrinho.observacao = item.observacao;

    } else {
      const itens = this._store.carrinho.itens;
      itens.push(item);
    }

    this.atualizarCarrinho();
  }

  obterItemCarrinho(item: Item): Item {
    return _.find(this._store.carrinho.itens, { produto: { idProduto: item.produto.idProduto } });
  }

  removerItemCarrinho(item: Item) {
    _.remove(this._store.carrinho.itens, { produto: { idProduto: item.produto.idProduto } });
    this.atualizarCarrinho();
  }

  limparCarrinho(): void {
    this._store = { carrinho: initializeCarrinho() };
    this.atualizarCarrinho();
  }

  getItens(): Item[] {
    return this._store.carrinho.itens;
  }
}
