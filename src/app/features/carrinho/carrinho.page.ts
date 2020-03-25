import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { filter } from 'rxjs/operators';
import { CarrinhoStatus } from 'src/app/core/domain/carrinho';
import { Item } from '../../core/domain/item';
import { PedidoStatus } from '../../core/domain/pedido';
import { AuthService } from '../../core/services/auth.service';
import { LojaService } from '../../core/services/loja.service';
import { PedidosService } from '../../core/services/pedidos.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { MENSAGENS } from '../../shared/mensagens/mensagens';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { toDateISOString } from '../../shared/utils/date-utils';

@Component({
  selector: 'app-carrinho',
  templateUrl: 'carrinho.page.html',
  styleUrls: [
    './carrinho.page.scss'
  ]
})
export class CarrinhoPage implements OnInit {

  itens$: Observable<Item[]>;
  pedidoStatus$: Observable<PedidoStatus>;
  carrinhoStatus$: Observable<CarrinhoStatus>;
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
    this.obterItensCarrinho();
    this.obterPedidoStatusCriar();
    this.obterCarrinhoStatus();
    this.onCarrinhoStatusChange();
  }

  ionViewDidEnter() {
    this.pedidoService.setPedidoStatusNaoEnviadoCriar();
  }

  private formularioCarrinhoInitialize() {
    const usuario = this.authService.getUsuarioLogado();
    return {
      dataPedido: toDateISOString(new Date()),
      idCliente: usuario.idCliente,
      idUsuario: usuario.idUsuario,
      observacao: '',
      idPedido: 0
    };
  }

  criarFormularioCarrinho() {
    this.carrinhoForm = this.formBuilder.group(
      this.formularioCarrinhoInitialize()
    );
  }

  onCarrinhoStatusChange() {
    this.carrinhoStatus$.pipe(filter(
      status => status === CarrinhoStatus.CHECKOUT)
    ).subscribe(() => this.carrinhoForm.patchValue(
      this.formularioCarrinhoInitialize()
    ));
  }

  obterItensCarrinho() {
    this.itens$ = this.lojaService.obterItensCarrinho();
  }

  obterPedidoStatusCriar() {
    this.pedidoStatus$ = this.pedidoService.obterPedidoStatusCriar();
  }

  obterCarrinhoStatus() {
    this.carrinhoStatus$ = this.lojaService.obterCarrinhoStatus();
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
