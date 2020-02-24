import { Component, OnInit } from '@angular/core';
import { ProdutosService } from '../core';
import { Produto } from '../core/domain';

@Component({
  selector: 'app-produtos',
  templateUrl: 'produtos.page.html',
  styleUrls: ['produtos.page.scss']
})
export class ProdutosPage implements OnInit {

  produtos = [];

  constructor(
    private produtosService: ProdutosService
  ) { }

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.listarMeusProdutosDisponiveis();
  }

  listarMeusProdutosDisponiveis() {
    this.produtosService.listarMeusProdutos().subscribe(
      produtos => {
        this.produtos = this.produtosService.listarMeusProdutosDisponiveis(produtos);
      }
    );
  }

  adicionarProdutoCarrinho(produto: Produto) {
    this.produtosService.adicionarProduto(this.produtos, produto);
  }

}
