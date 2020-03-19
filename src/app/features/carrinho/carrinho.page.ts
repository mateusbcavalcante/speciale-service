import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { Item } from '../../core/domain/item';
import { PedidoStatus } from '../../core/domain/pedido';
import { AuthService } from '../../core/services/auth.service';
import { LojaService } from '../../core/services/loja.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { toDateISOString } from '../../shared/utils/date-utils';
import { MENSAGENS } from '../../shared/mensagens/mensagens';
import { PedidosService } from '../../core/services/pedidos.service';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';

@Component({
  selector: 'app-carrinho',
  templateUrl: 'carrinho.page.html'
})
export class CarrinhoPage implements OnInit {

  itens$: Observable<Item[]>;
  pedidoStatus$: Observable<PedidoStatus>;

  carrinhoForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private lojaService: LojaService,
    private pedidoService: PedidosService,
    private authService: AuthService,
    private alertService: AlertService,
    private notificacaoService: NotificacaoService
  ) { }

  ngOnInit() {
    this.criarFormularioCarrinho();
    this.obterItensCarriho();
    this.obterPedidoStatusCriar();
  }

  ionViewWillEnter() {
    this.pedidoService.setPedidoStatusNaoEnviadoCriar();
  }

  criarFormularioCarrinho() {
    const usuario = this.authService.getUsuarioLogado();
    this.carrinhoForm = this.formBuilder.group({
      dataPedido: [toDateISOString(new Date())],
      idCliente: [usuario.idCliente],
      idUsuario: [usuario.idUsuario],
      idPedido: [0],
    });
  }

  obterItensCarriho() {
    this.itens$ = this.lojaService.obterItensCarrinho();
  }

  obterPedidoStatusCriar() {
    this.pedidoStatus$ = this.pedidoService.obterPedidoStatusCriar();
  }

  limparCarrinho() {
    this.lojaService.limparCarrinho();
  }

  removerItemCarrinho(item: Item) {
    this.lojaService.removerItemCarrinho(item);
  }

  removerItemCarrinhoConfirmacao(event: any) {
    const item = event.item;
    this.alertService.confirm(
      'Confirmação',
      MENSAGENS.CONFIMAR_REMOVER_ITEM_CARRINHO(item.produto),
      () => this.removerItemCarrinho(item)
    );
  }

  async registrarPedido() {
    try {
      this.pedidoService.cadastrarNovoPedidoForm(this.carrinhoForm.value);
    } catch (error) {
      await this.notificacaoService.showErrorToaster(error.message);
    }

  }
}
