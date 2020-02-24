import { Component, OnInit } from '@angular/core';
import { NotificacaoService, PedidoService, AuthService, AlertService } from '../core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Pedido } from '../core/domain';


@Component({
  selector: 'app-pedidos',
  templateUrl: 'pedidos.page.html',
  styleUrls: ['pedidos.page.scss']
})
export class PedidosPage implements OnInit {

  pedido: Pedido;

  pedidosPesquisaForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private notificacaoService: NotificacaoService,
    private pedidoService: PedidoService,
    private authService: AuthService,
    private alertService: AlertService,
  ) { }

  ngOnInit() {
    this.criarFormularioPesquisaPedidos();
  }

  ionViewWillEnter() {
    this.pedido = null;
  }

  criarFormularioPesquisaPedidos() {
    const usuario = this.authService.getUsuarioLogado();
    this.pedidosPesquisaForm = this.formBuilder.group({
      dataPedido: [new Date().toISOString(), Validators.required],
      idCliente: [usuario.idCliente]
    });
  }

  obterPedidoParametrosPesquisa() {
    this.pedido = null;
    this.pedidoService.obterPedidoPorParametrosPesquisa(this.pedidosPesquisaForm.value).subscribe(
      pedido => {
        this.pedido = pedido;
      }
    );
  }

  pesquisarPedidos() {
    this.obterPedidoParametrosPesquisa();
  }

  inativarPedido() {
    this.pedidoService.inativarPedido(this.pedido).subscribe(
      async data => {
        await this.notificacaoService.showSuccessToaster('Pedido inativado com sucesso !!!');
        this.pedido = null;
      }
    );
  }

  inativarPedidoConfirmacao() {
    this.alertService.confirm(
      'Confirmação',
      `Deseja realmente inativar o pedido: ${this.pedido.idPedido} ?`,
      () => this.inativarPedido()
    );
  }

}
