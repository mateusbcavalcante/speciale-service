import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { Observable } from 'rxjs';
import { ObterPedidoDto } from '../../core/domain/obter-pedido.dto';
import { Pedido } from '../../core/domain/pedido';
import { PedidosService } from '../../core/services/pedidos.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { MENSAGENS } from '../../shared/mensagens/mensagens';
import { toDateISOString } from '../../shared/utils/date-utils';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';

@Component({
  selector: 'app-pedidos',
  templateUrl: 'pedidos.page.html',
  styleUrls: [
    'pedidos.page.scss'
  ]
})
export class PedidosPage implements OnInit {

  pedido$: Observable<Pedido>;
  filtros: ObterPedidoDto;

  constructor(
    private pedidoService: PedidosService,
    private alertService: AlertService,
    private navCtrl: NavController,
    private notificacaoService: NotificacaoService
  ) { }

  ngOnInit() {
    this.criarPesquisaPedidosModel();
    this.pedido$ = this.pedidoService.obterPedido();
  }

  criarPesquisaPedidosModel() {
    this.filtros = new ObterPedidoDto({
      dataPedido: toDateISOString(new Date()),
      idPedido: null
    });
  }

  limparDateTimeField(event: any) {
    this.filtros.dataPedido = null;
  }

  async pesquisarPedidos() {
    if (this.pedidoService.isFiltroPesquisaValido(this.filtros)) {
      this.pedidoService.pesquisarPedido(this.filtros);
    } else {
      await this.notificacaoService.showErrorToaster(MENSAGENS.PESQUISA_PEDIDO_VALIDACAO);
    }
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
