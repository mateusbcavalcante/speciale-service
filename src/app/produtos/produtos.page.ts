import { Component, OnInit } from '@angular/core';
import { LoadingController } from '@ionic/angular';
import { NotificacaoService, ProdutosService, Produto } from '../core';

@Component({
  selector: 'app-produtos',
  templateUrl: 'produtos.page.html',
  styleUrls: ['produtos.page.scss']
})
export class ProdutosPage implements OnInit {

  produtos = [];

  constructor(
    private produtosService: ProdutosService,
    private notificacaoService: NotificacaoService,
    private loadingCtrl: LoadingController,
  ) { }

  ngOnInit() {
  }

  async ionViewWillEnter() {
    await this.listarMeusProdutosDisponiveis();
  }

  async listarMeusProdutosDisponiveis() {
    const loading = await this.loadingCtrl.create({
      message: 'Carregando...'
    });
    await loading.present();
    this.produtosService.listarMeusProdutos().subscribe(
      async produtos => {
        await loading.dismiss();
        this.produtos = this.produtosService.listarMeusProdutosDisponiveis(produtos);
      },
      async e => {
        await loading.dismiss();
        await this.notificacaoService.showErrorToaster(e.error.message);
      }
    );
  }

  async adicionarProdutoCarrinho(produto: Produto) {
    if (this.produtosService.produtoJaAdicionadoCarrinho(produto)) {
      await this.notificacaoService.showErrorToaster('Produto já adicionado');

    } else if (!this.produtosService.qtdeSolicitadaValida(produto)) {
      await this.notificacaoService.showErrorToaster('Quantidade do produto está inválida');

    } else {
      this.produtosService.adicionarProdutoListaCarrinho(produto);
      this.produtosService.removerProdutoLista(this.produtos, produto);
      await this.notificacaoService.showInfoToaster(`Produto adicionado no carrinho`);
    }
  }

}
