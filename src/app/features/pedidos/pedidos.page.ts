import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NavController } from '@ionic/angular';
import { Observable } from 'rxjs';
import { toDateISOString } from '../../shared/utils/date-utils';
import { Pedido } from '../../core/domain/pedido';
import { AuthService } from '../../core/services/auth.service';
import { PedidosService } from '../../core/services/pedidos.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { MENSAGENS } from '../../shared/mensagens/mensagens';

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
    private pedidoService: PedidosService,
    private authService: AuthService,
    private alertService: AlertService,
    private navCtrl: NavController
  ) { }

  ngOnInit() {
    this.criarFormularioPesquisaPedidos();
    this.pedido$ = this.pedidoService.obterPedido();
  }

  criarFormularioPesquisaPedidos() {
    const usuario = this.authService.getUsuarioLogado();
    this.pedidosPesquisaForm = this.formBuilder.group({
      dataPedido: [toDateISOString(new Date())],
      idCliente: [usuario.idCliente]
    });
  }

  pesquisarPedidos() {
    this.pedidoService.pesquisarPedido(this.pedidosPesquisaForm.value);
  }

  changePeriodo(event: any) {
    this.pesquisarPedidos();
  }

  inativarPedido(pedido: Pedido) {
    this.pedidoService.inativarPedido(pedido);
  }

  editarPedido(event: any) {
    const pedido = event.pedido;
    this.pedidoService.carregarPedidoEdicao(pedido);
    this.navCtrl.navigateForward(`/app/pedidos/${pedido.idPedido}`);
  }

  inativarPedidoConfirmacao(event: any) {
    const pedido = event.pedido;
    this.alertService.confirm(
      'Confirmação',
      MENSAGENS.INATIVAR_PEDIDO(pedido),
      () => this.inativarPedido(pedido)
    );
  }

}
