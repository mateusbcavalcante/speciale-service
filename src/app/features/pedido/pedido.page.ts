import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Pedido } from '../../core/domain/pedido';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { PedidosService } from '../../core/services/pedidos.service';
import { AuthService } from '../../core/services/auth.service';
import { AlertService } from '../../shared/alertas/alert.service';
import { Observable } from 'rxjs';
import { Produto } from '../../core/domain/produto';


@Component({
  selector: 'app-pedido',
  templateUrl: 'pedido.page.html',
  styleUrls: [
    'pedido.page.scss'
  ]
})
export class PedidoPage implements OnInit {

  pedido$: Observable<Pedido>;

  pedidoForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private pedidoService: PedidosService,
    private authService: AuthService,
    private alertService: AlertService,
  ) { }

  ngOnInit() {
    this.criarFormularioPedido();
    this.pedido$ = this.pedidoService.pedido;
    this.pedidoService.obterPedidoPorId(19495);
    this.populateForm();
  }

  criarFormularioPedido() {
    const usuario = this.authService.getUsuarioLogado();
    this.pedidoForm = this.formBuilder.group({
      dataPedido: ['', Validators.required],
      idCliente: [usuario.idCliente],
      observacao: ['']
    });
  }

  populateForm() {
    this.pedidoForm.patchValue(this.pedidoService.getPedido());
  }

  setObservacao(event: any) {
    console.log(event.target.value);
  }

  removerProdutoPedido(produto: Produto) {
    this.pedidoService.removerProdutoPedido(produto);
  }

  removerProdutoPedidoConfirmacao(event: any) {
    const produto = event.produto;
    this.alertService.confirm(
      'Confirmação',
      `Deseja remover o produto :
      ${produto.desProduto} ?`,
      () => this.removerProdutoPedido(produto)
    );
  }

  alterarPedido() {

  }
}
