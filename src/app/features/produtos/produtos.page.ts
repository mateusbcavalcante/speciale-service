import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Produto } from '../../core/domain/produto';
import { Observable } from 'rxjs';
import { LojaService } from '../../core/services/loja.service';

@Component({
  selector: 'app-produtos',
  templateUrl: 'produtos.page.html'
})
export class ProdutosPage implements OnInit {

  produtos$: Observable<Produto[]>;

  constructor(
    private authService: AuthService,
    private lojaService: LojaService
  ) { }

  ngOnInit() {
    this.obterProdutosDisponiveis();
    this.pesquisarMeusProdutos();
  }

  obterProdutosDisponiveis() {
    this.produtos$ = this.lojaService.obterProdutosDisponiveis();
  }

  pesquisarMeusProdutos() {
    const usuario = this.authService.getUsuarioLogado();
    this.lojaService.listarProdutosPorCliente(usuario.idCliente);
  }

  adicionarProdutoCarrinho(event: any) {
    const produto = event.produto;
    this.lojaService.addProdutoCarrinho(produto);
  }

}
