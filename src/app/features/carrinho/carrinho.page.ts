import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ProdutosService } from '../../core/services/produtos.service';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { PedidosService } from '../../core/services/pedidos.service';
import { AuthService } from '../../core/services/auth.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { Produto } from 'src/app/core/domain/produto';

@Component({
  selector: 'app-carrinho',
  templateUrl: 'carrinho.page.html'
})
export class CarrinhoPage implements OnInit {

  produtos = [];

  carrinhoForm: FormGroup;

  mensagem = '';

  constructor(
    private formBuilder: FormBuilder,
    private produtosService: ProdutosService,
    private notificacaoService: NotificacaoService,
    private pedidoService: PedidosService,
    private authService: AuthService,
    private alertServive: AlertService
  ) { }

  ngOnInit() {
    this.criarFormularioCarrinho();
  }

  ionViewWillEnter() {
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
    this.mensagem = `Você tem ${this.produtos.length} produtos no carrinho`;
  }

  removerProdutoCarrinho(event: any) {
    const produto = event.produto;
    if (produto) {
      this.produtosService.removerProdutoListaCarrinho(produto);
      this.listarProdutosCarrinho();
    }
  }

  registrarPedido() {

    const pedido = this.carrinhoForm.value;
    pedido.produtos = this.produtos;

    this.pedidoService.cadastrarPedido(pedido).subscribe(
      async data => {
        await this.notificacaoService.showSuccessToaster(
          `Seu pedido de número: '${data.idPedido}' foi enviado para Speciale. Obrigado!!!`, 5000);
        this.limparCarrinho();
      }
    );
  }

}
