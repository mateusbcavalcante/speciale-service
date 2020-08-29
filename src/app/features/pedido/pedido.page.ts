import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { Observable } from 'rxjs';
import { ListaProdutosComponent } from '../../components/lista-produtos/lista-produtos.component';
import { Pedido, PedidoStatus } from '../../core/domain/pedido';
import { Produto } from '../../core/domain/produto';
import { PedidosService } from '../../core/services/pedidos.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { MENSAGENS } from '../../shared/mensagens/mensagens';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';


@Component({
  selector: 'app-pedido',
  templateUrl: 'pedido.page.html',
  styleUrls: [
    'pedido.page.scss'
  ]
})
export class PedidoPage implements OnInit {

  pedidoEdicao$: Observable<Pedido>;
  pedidoStatus$: Observable<PedidoStatus>;
  produtos$: Observable<Produto[]>;
  pedidoForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private pedidosService: PedidosService,
    private alertService: AlertService,
    private modalController: ModalController,
    private notificacaoService: NotificacaoService,
  ) { }

  ngOnInit() {
    this.criarFormularioPedido();
    this.carregarPedidoEdicao();
    this.carregarPedidoStatusAlterar();
    this.populateForm();
  }

  ionViewDidEnter() {
    this.carregarProdutos();
  }

  criarFormularioPedido() {
    this.pedidoForm = this.formBuilder.group({
      idPedido: [0],
      dataPedido: [],
      idCliente: [0],
      idUsuario: [0],
      idOpcaoEntrega: [0],
      observacao: []
    });
  }

  carregarPedidoStatusAlterar() {
    this.pedidoStatus$ = this.pedidosService.obterPedidoStatusAlterar();
  }

  carregarPedidoEdicao() {
    this.pedidoEdicao$ = this.pedidosService.obterPedidoEdicao();
  }

  carregarProdutos() {
    this.produtos$ = this.pedidosService.obterProdutos();
    this.pedidosService.listarProdutosPorCliente(this.pedidoForm.controls.idCliente.value);
  }

  populateForm() {
    const pedido = this.pedidosService.getPedidoEdicaoValue();
    this.pedidoForm.patchValue({
      idPedido: pedido.idPedido,
      idCliente: pedido.idCliente,
      idUsuario: pedido.idUsuario,
      dataPedido: pedido.dataPedido.toISOString(),
      idOpcaoEntrega: pedido.idOpcaoEntrega.toString(),
      observacao: pedido.observacao
    });
  }

  async listarProdutoSelecao(event: any) {
    const modal = await this.modalController.create({
      component: ListaProdutosComponent,
      mode: 'md',
      componentProps: {
        produtos: this.produtos$
      }
    });

    modal.onDidDismiss().then(
      (result) => {
        if (result.data) {
          this.pedidosService.addProdutoPedido(result.data as Produto);
        }
      });

    return await modal.present();
  }

  removerProdutoPedido(produto: Produto) {
    this.pedidosService.removerProdutoPedido(produto);
  }

  removerProdutoPedidoConfirmacao(event: any) {
    const produto = event.produto;
    this.alertService.confirm(
      'Confirmação',
      MENSAGENS.CONFIMAR_REMOVER_PRODUTO(produto),
      () => this.removerProdutoPedido(produto)
    );
  }

  async alterarPedido() {
    try {
      this.pedidosService.atualizarPedidoForm(this.pedidoForm.value);
    } catch (error) {
      await this.notificacaoService.showErrorToaster(error.message);
    }
  }
}
