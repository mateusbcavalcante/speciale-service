import { Component, OnInit } from '@angular/core';
import { ProdutosService } from '../../core/services/produtos.service';

@Component({
  selector: 'app-produtos',
  templateUrl: 'produtos.page.html'
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

  adicionarProdutoCarrinho(event: any) {
    const produto = event.produto;
    this.produtosService.adicionarProduto(this.produtos, produto);
  }

}
