import { Component, OnInit } from '@angular/core';
import { ProdutosService, NotificacaoService, PedidoService, AuthService, AlertService } from '../core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoadingController } from '@ionic/angular';

@Component({
  selector: 'app-carrinho',
  templateUrl: 'carrinho.page.html',
  styleUrls: ['carrinho.page.scss']
})
export class CarrinhoPage implements OnInit {

  produtos = [];

  carrinhoForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private produtosService: ProdutosService,
    private notificacaoService: NotificacaoService,
    private pedidoService: PedidoService,
    private authService: AuthService,
    private alertServive: AlertService,
    private loadingCtrl: LoadingController,
  ) { }

  ngOnInit() {
    this.criarFormularioCarrinho();
  }

  async ionViewWillEnter() {
    this.listarProdutosCarrinho();
  }

  criarFormularioCarrinho() {
    const usuario = this.authService.getUsuarioLogado();
    this.carrinhoForm = this.formBuilder.group({
      dataPedido: [new Date().toISOString(), Validators.required],
      observacao: [''],
      idCliente: [usuario.idCliente],
      idUsuario: [usuario.idUsuario],
      idPedido: [0],
    });
  }

  async limparCarrinhoConfirmacao() {
    await this.alertServive.confirm('Confirmação', 'Deseja limpar seu carrinho ?', () => this.limparCarrinho());
  }

  async limparCarrinho() {
    this.produtosService.limparCarrinho();
    this.listarProdutosCarrinho();
    this.carrinhoForm.get('observacao').setValue('');
  }

  listarProdutosCarrinho() {
    this.produtos = this.produtosService.getListaProdutosCarrinho();
  }

  async removerProdutoCarrinho(produto: any) {
    this.produtosService.removerProdutoListaCarrinho(produto);
    this.listarProdutosCarrinho();
    await this.notificacaoService.showInfoToaster('Produto removido do carrinho');
  }

  async registrarPedido() {
    const loading = await this.loadingCtrl.create({
      message: 'Enviando...'
    });
    await loading.present();

    this.pedidoService.cadastrarPedido(this.carrinhoForm.value, this.produtos).subscribe(
      async data => {
        await loading.dismiss();
        await this.notificacaoService.showSuccessToaster(
          `Seu pedido de número: '${data.idPedido}' foi enviado para Speciale. Obrigado!!!`, 5000);
        this.limparCarrinho();
      },
      async error => {
        await loading.dismiss();
        await this.notificacaoService.showErrorToaster(error.error.message);
      }
    );
  }

}
