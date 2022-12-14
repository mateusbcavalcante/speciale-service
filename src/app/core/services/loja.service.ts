import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Item } from '../domain/item';
import { Produto } from '../domain/produto';
import { CarrinhoStore } from '../store/carrinho.store';
import { ProdutosStore } from '../store/produtos.store';
import { ApiService } from './api.service';
import { CarrinhoStatus } from '../domain/carrinho';


@Injectable({
  providedIn: 'root',
})
export class LojaService {

  constructor(
    protected apiService: ApiService,
    private produtosStore: ProdutosStore,
    private carrinhoStore: CarrinhoStore
  ) { }

  listarProdutosPorCliente(idCliente?: number) {
    this.apiService.get<Produto[]>(`/produtos/clientes/${idCliente}`)
      .subscribe(
        (produtos: Produto[]) => this.produtosStore.carregarProdutos(produtos)
      );
  }

  obterProdutosDisponiveis(): Observable<Produto[]> {
    return this.produtosStore.state$
      .pipe(
        map(produtosState => produtosState.disponiveis)
      );
  }

  obterItensCarrinho(): Observable<Item[]> {
    return this.carrinhoStore.state$
      .pipe(
        map(carrinhoState => carrinhoState.itens)
      );
  }

  obterCarrinhoStatus(): Observable<CarrinhoStatus> {
    return this.carrinhoStore.state$
      .pipe(
        map(carrinhoState => carrinhoState.status)
      );
  }

  addProdutoCarrinho(produto: Produto) {
    this.carrinhoStore.addProdutoCarrinho(produto);
    this.produtosStore.removerProduto(produto);
  }

  removerItemCarrinho(item: Item) {
    item.produto.qtdSolicitada = 0;
    this.carrinhoStore.removerItemCarrinho(item);
    this.produtosStore.addProduto(item.produto);
  }

  limparCarrinho(): void {
    this.carrinhoStore.limparCarrinho();
  }

  checkoutCarrinho(): void {
    this.carrinhoStore.checkoutCarrinho();
  }

}
