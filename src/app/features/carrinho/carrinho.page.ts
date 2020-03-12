import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { Item } from '../../core/domain/item';
import { AuthService } from '../../core/services/auth.service';
import { LojaService } from '../../core/services/loja.service';
import { PedidosService } from '../../core/services/pedidos.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { setInputDateTimeValue } from '../../shared/utils/form-utils';

@Component({
  selector: 'app-carrinho',
  templateUrl: 'carrinho.page.html'
})
export class CarrinhoPage implements OnInit {

  itens$: Observable<Item[]>;

  carrinhoForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private lojaService: LojaService,
    private pedidosService: PedidosService,
    private authService: AuthService,
    private alertService: AlertService,
    private notificacaoService: NotificacaoService
  ) { }

  ngOnInit() {
    this.criarFormularioCarrinho();
    this.obterItensCarriho();
  }

  criarFormularioCarrinho() {
    const usuario = this.authService.getUsuarioLogado();
    this.carrinhoForm = this.formBuilder.group({
      dataPedido: [setInputDateTimeValue(new Date())],
      idCliente: [usuario.idCliente],
      idUsuario: [usuario.idUsuario],
      idPedido: [0],
    });
  }

  obterItensCarriho() {
    this.itens$ = this.lojaService.obterItensCarrinho();
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
    const pedido = this.lojaService.montarPedido(this.carrinhoForm.value);
    this.pedidosService.cadastrarNovoPedido(pedido).subscribe(
      async data => {
        await this.notificacaoService.showSuccessToaster(`Pedido ${data.idPedido} foi enviado para Speciale. Obrigado!!!`);
        this.limparCarrinho();
      }
    );
  }
}
