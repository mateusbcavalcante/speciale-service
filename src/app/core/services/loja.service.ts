import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Item } from '../domain/item';
import { Pedido, PedidoStatus } from '../domain/pedido';
import { Produto } from '../domain/produto';
import { CarrinhoStore } from '../store/carrinho.store';
import { PedidoStore } from '../store/pedido.store';
import { ProdutosStore } from '../store/produtos.store';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class LojaService {

  constructor(
    protected apiService: ApiService,
    private produtosStore: ProdutosStore,
    private carrinhoStore: CarrinhoStore,
    private pedidoStore: PedidoStore
  ) { }

  listarProdutosPorCliente(idCliente?: number) {
    this.apiService.get<Produto[]>(`/produtos/clientes/${idCliente}`)
      .subscribe(
        (produtos: Produto[]) => this.produtosStore.carregarProdutos(produtos)
      );
  }

  cadastrarNovoPedido(pedido: Pedido) {
    this.pedidoStore.setStatusEnviandoCriar();
    this.apiService.post<Pedido>(`/pedidos`, pedido).subscribe(
      data => {
        this.limparCarrinho();
        this.pedidoStore.setStatusEnviadoCriar(`Seu pedido de número ${data.idPedido} foi enviado para Speciale com sucesso!!`);
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

  montarPedidoParaCadastro(data: any): Pedido {
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
