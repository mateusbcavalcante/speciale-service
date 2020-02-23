import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { StorageService } from './storage.service';
import { AuthService } from './auth.service';
import { Produto } from '../domain/produto';
import { Observable } from 'rxjs';
import * as _ from 'lodash';

@Injectable()
export class ProdutosService {

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private storageService: StorageService) {
  }

  listarMeusProdutos(): Observable<Produto[]> {
    const usuario = this.authService.getUsuarioLogado();
    return this.http.get<Produto[]>(`${environment.base_url}/produtos/clientes/${usuario.idCliente}`);
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

  adicionarProdutoListaCarrinho(produto: Produto) {
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

  limparCarrinho(): void{
    this.storageService.removeItem('lista_produtos_pedido');
  }
}
