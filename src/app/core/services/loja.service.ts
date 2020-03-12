import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { ProdutosStore } from '../store/produtos.store';
import { CarrinhoStore } from '../store/carrinho.store';
import { Produto } from '../domain/produto';
import { Pedido } from '../domain/pedido';
import { Item } from '../domain/item';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

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
    this.produtosStore.restoreProdutos();
    this.carrinhoStore.limparCarrinho();
  }

  montarPedido(data: any): Pedido {
    const pedido: Pedido = data;
    pedido.observacao = '';
    this.carrinhoStore.state.itens.forEach(
      item => {
        if (item.observacao) {
          pedido.observacao += `${item.produto.desProduto} : ${item.observacao}\n\n`;
        }
      });
    pedido.produtos = this.carrinhoStore.state.produtos;
    return pedido;
  }

}
