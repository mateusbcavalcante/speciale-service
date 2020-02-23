import { Component, OnInit } from '@angular/core';
import { NotificacaoService, PedidoService, AuthService, Pedido, AlertService } from '../core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoadingController } from '@ionic/angular';

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
    private loadingCtrl: LoadingController,
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

  async obterPedidoParametrosPesquisa() {
    const loading = await this.loadingCtrl.create({
      message: 'Pesquisando...'
    });
    await loading.present();

    this.pedido = null;

    this.pedidoService.obterPedidoPorParametrosPesquisa(this.pedidosPesquisaForm.value).subscribe(
      async pedido => {
        await loading.dismiss();
        this.pedido = pedido;
      },
      async error => {
        await loading.dismiss();
        await this.notificacaoService.showErrorToaster(error.error.message);
      }
    );
  }

  async pesquisarPedidos() {
    await this.obterPedidoParametrosPesquisa();
  }

  async inativarPedido() {
    this.pedidoService.inativarPedido(this.pedido).subscribe(
      async data => {
        await this.notificacaoService.showSuccessToaster('Pedido inativado com sucesso !!!');
        this.pedido = null;
      },
      async error => await this.notificacaoService.showErrorToaster(error.error.message)
    );
  }

  async inativarPedidoConfirmacao() {
    this.alertService.confirm('Confirmação', `Deseja realmente inativar o pedido: ${this.pedido.idPedido} ?`,
      async () => {
        await this.inativarPedido();
      }
    );
  }

}
