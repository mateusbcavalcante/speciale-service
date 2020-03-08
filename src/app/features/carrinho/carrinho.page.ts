import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { CarrinhoService } from '../../core/services/carrinho.service';
import { PedidosService } from '../../core/services/pedidos.service';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { Item } from '../../core/domain/item';
import { Carrinho } from '../../core/domain/carrinho';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-carrinho',
  templateUrl: 'carrinho.page.html'
})
export class CarrinhoPage implements OnInit {

  carrinho$: Observable<Carrinho>;

  carrinhoForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private carrinhoService: CarrinhoService,
    private pedidosService: PedidosService,
    private authService: AuthService,
    private alertService: AlertService,
    private notificacaoService: NotificacaoService
  ) { }

  ngOnInit() {
    this.criarFormularioCarrinho();
    this.obterCarriho();
  }

  criarFormularioCarrinho() {
    const usuario = this.authService.getUsuarioLogado();
    this.carrinhoForm = this.formBuilder.group({
      dataPedido: [new Date().toISOString(), Validators.required],
      idCliente: [usuario.idCliente],
      idUsuario: [usuario.idUsuario],
      idPedido: [0],
    });
  }

  obterCarriho() {
    this.carrinho$ = this.carrinhoService.carrinho;
  }

  limparCarrinho() {
    this.carrinhoService.limparCarrinho();
  }

  removerItemCarrinho(item: Item) {
    this.carrinhoService.removerItemCarrinho(item);
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

    const pedido = this.carrinhoForm.value;
    const itens = this.carrinhoService.getItens();

    this.pedidosService.cadastrarNovoPedido(pedido, itens).subscribe(
      async data => {
        await this.notificacaoService.showSuccessToaster(
          `Seu pedido de número: '${data.idPedido}' foi enviado para Speciale. Obrigado!!!`, 5000);
        this.limparCarrinho();
      }
    );
  }

}
