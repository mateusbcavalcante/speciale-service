import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { Item } from '../../core/domain/item';
import { PedidoStatus } from '../../core/domain/pedido';
import { AuthService } from '../../core/services/auth.service';
import { LojaService } from '../../core/services/loja.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { toDateISOString } from '../../shared/utils/date-utils';

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
    private authService: AuthService,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.criarFormularioCarrinho();
    this.obterItensCarriho();
    this.obterPedidoStatusCriar();
  }

  ionViewWillEnter() {
    this.lojaService.setPedidoStatusNaoEnviadoCriar();
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
    this.pedidoStatus$ = this.lojaService.obterPedidoStatusCriar();
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
      `Deseja remover o item :
      ${item.produto.desProduto} ?`,
      () => this.removerItemCarrinho(item)
    );
  }

  registrarPedido() {
    const pedido = this.lojaService.montarPedidoParaCadastro(this.carrinhoForm.value);
    this.lojaService.cadastrarNovoPedido(pedido);
  }
}
