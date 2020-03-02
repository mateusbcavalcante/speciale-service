import { Component, OnInit } from '@angular/core';
import { ProdutosService } from '../../core/services/produtos.service';
import { AuthService } from '../../core/services/auth.service';
import { Produto } from '../../core/domain/produto';
import { CarrinhoService } from '../../core/services/carrinho.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-produtos',
  templateUrl: 'produtos.page.html'
})
export class ProdutosPage implements OnInit {

  produtos$: Observable<Produto[]>;

  constructor(
    private authService: AuthService,
    private produtosService: ProdutosService,
    private carrinhoService: CarrinhoService
  ) { }

  ngOnInit() {
    this.produtos$ = this.produtosService.produtos;
    this.pesquisarMeusProdutos();
  }

  pesquisarMeusProdutos() {
    const usuario = this.authService.getUsuarioLogado();
    this.produtosService.listarProdutosCliente(usuario.idCliente);
  }

  adicionarProdutoCarrinho(event: any) {
    const produto = event.produto;
    this.carrinhoService.addItemCarrinho({
      produto
    });
  }

}
