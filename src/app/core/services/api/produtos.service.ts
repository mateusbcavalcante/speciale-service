import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { AuthService } from './auth.service';
import { StorageService } from '../utils';
import { Produto, AppError } from '../../domain';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root',
})
export class ProdutosService {

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private storageService: StorageService) {
  }

  listarMeusProdutos(): Observable<Produto[]> {
    const usuario = this.authService.getUsuarioLogado();
    return this.apiService.get<Produto[]>(`/produtos/clientes/${usuario.idCliente}`);
  }

  listarMeusProdutosDisponiveis(todosProdutos: Produto[]) {
    const produtosAdicionados = this.getListaProdutosCarrinho();
    _.pullAllWith(todosProdutos, produtosAdicionados, (produto1: any, produto2: any) => {
      return produto1.idProduto === produto2.idProduto;
    });
    return todosProdutos;
  }

  produtoJaAdicionadoCarrinho(produto: Produto): boolean {
    const listaProdutosPedido = this.getListaProdutosCarrinho();
    const produtoExistente = _.find(listaProdutosPedido, { idProduto: produto.idProduto });
    return !!produtoExistente;
  }

  qtdeSolicitadaValida(produto: Produto): boolean {
    return !(!produto.qtdSolicitada || produto.qtdSolicitada === 0);
  }

  adicionarProduto(produtos: Produto[], produto: Produto): boolean {
    if (this.produtoJaAdicionadoCarrinho(produto)) {
      throw new AppError('Produto já adicionado');

    } else if (!this.qtdeSolicitadaValida(produto)) {
      throw new AppError('Quantidade do produto está inválida');

    } else {
      this.adicionarProdutoListaPedido(produto);
      this.removerProdutoLista(produtos, produto);
      return true;
    }
  }



  adicionarProdutoListaPedido(produto: Produto) {
    const listaProdutosPedido = this.getListaProdutosCarrinho();
    listaProdutosPedido.push(produto);
    this.storageService.setJson('lista_produtos_pedido', listaProdutosPedido);
  }

  removerProdutoListaCarrinho(produto: Produto) {
    const listaProdutosPedido = this.getListaProdutosCarrinho();
    this.removerProdutoLista(listaProdutosPedido, produto);
    this.storageService.setJson('lista_produtos_pedido', listaProdutosPedido);
  }

  removerProdutoLista(produtos: Produto[], produto: Produto) {
    _.remove(produtos, { idProduto: produto.idProduto });
  }

  getListaProdutosCarrinho() {
    return this.storageService.getJson('lista_produtos_pedido', []);
  }

  limparCarrinho(): void {
    this.storageService.removeItem('lista_produtos_pedido');
  }
}
