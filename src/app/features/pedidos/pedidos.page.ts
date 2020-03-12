import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Pedido } from '../../core/domain/pedido';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { PedidosService } from '../../core/services/pedidos.service';
import { AuthService } from '../../core/services/auth.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { setInputDateTimeValue } from '../../shared/utils/form-utils';
import { Observable } from 'rxjs';
import { NavController } from '@ionic/angular';


@Component({
  selector: 'app-pedidos',
  templateUrl: 'pedidos.page.html',
  styleUrls: [
    'pedidos.page.scss'
  ]
})
export class PedidosPage implements OnInit {

  pedido$: Observable<Pedido>;

  pedidosPesquisaForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private notificacaoService: NotificacaoService,
    private pedidoService: PedidosService,
    private authService: AuthService,
    private alertService: AlertService,
    private navCtrl: NavController,
  ) { }

  ngOnInit() {
    this.criarFormularioPesquisaPedidos();
    this.pedido$ = this.pedidoService.obterPedido();
  }

  criarFormularioPesquisaPedidos() {
    const usuario = this.authService.getUsuarioLogado();
    this.pedidosPesquisaForm = this.formBuilder.group({
      dataPedido: [setInputDateTimeValue(new Date())],
      idCliente: [usuario.idCliente]
    });
  }

  obterPedidoParametrosPesquisa() {
    this.pedidoService.pesquisarPedido(this.pedidosPesquisaForm.value);
  }

  pesquisarPedidos() {
    this.obterPedidoParametrosPesquisa();
  }

  inativarPedido(pedido: Pedido) {
    this.pedidoService.inativarPedido(pedido);
  }

  editarPedido(event: any) {
    const pedido = event.pedido;
    this.navCtrl.navigateForward(`/app/pedidos/${pedido.idPedido}`);
  }

  inativarPedidoConfirmacao(event: any) {
    const pedido = event.pedido;
    this.alertService.confirm(
      'Confirmação',
      `Deseja realmente inativar o pedido:
      ${pedido.idPedido} ?`,
      () => this.inativarPedido(pedido)
    );
  }

}
