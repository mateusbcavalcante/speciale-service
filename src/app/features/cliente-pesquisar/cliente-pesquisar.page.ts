import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/core/domain/cliente';
import { ObterClienteDto } from 'src/app/core/domain/obter-cliente.dto';
import { AuthService } from 'src/app/core/services/auth.service';
import { ClientesService } from 'src/app/core/services/clientes.service';
import { LojaService } from 'src/app/core/services/loja.service';
import { PedidosService } from 'src/app/core/services/pedidos.service';
import { MENSAGENS } from 'src/app/shared/mensagens/mensagens';
import { NotificacaoService } from 'src/app/shared/notificacao/notificacao.service';

@Component({
  selector: 'app-cliente-pesquisar',
  templateUrl: 'cliente-pesquisar.page.html',
  styleUrls: [
    'cliente-pesquisar.page.scss'
  ]
})
export class ClientePesquisarPage implements OnInit {

  clientes: Cliente[];
  filtros: ObterClienteDto;

  constructor(
    private clientesService: ClientesService,
    private notificacaoService: NotificacaoService,
    private lojaService: LojaService,
    private pedidosService: PedidosService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.criarPesquisaClientesModel();
  }

  criarPesquisaClientesModel() {
    this.filtros = new ObterClienteDto({
      nomeCliente: null
    });
  }

  async pesquisarClientes() {
    this.clientes = [];
    if (this.clientesService.isFiltroPesquisaValido(this.filtros)) {
      this.clientesService.pesquisarClientes(this.filtros).subscribe(data => {
      this.clientes = data;
    });
    } else {
      await this.notificacaoService.showErrorToaster(MENSAGENS.PESQUISA_CLIENTE_VALIDACAO);
    }
  }

  async selecionarCliente(event: any) {
    const cliente = event.cliente;
    this.setCliente(cliente);
    this.setUsuario(cliente);
    this.resetLoja(cliente);
    this.resetPedido();
    await this.notificacaoService.showSuccessToaster(MENSAGENS.CLIENTE_SELECIONADO(cliente));
  }

  setCliente(cliente: Cliente) {
    this.clientesService.setCliente(cliente);
  }

  setUsuario(cliente: Cliente) {
    const usuarioLogado = this.authService.getUsuarioLogado();
    usuarioLogado.idCliente = cliente.idCliente;
    this.authService.setUsuario(usuarioLogado);
  }

  resetLoja(cliente: Cliente) {
    this.lojaService.checkoutCarrinho();
    this.lojaService.listarProdutosPorCliente(cliente.idCliente);
  }

  resetPedido() {
    this.pedidosService.resetarPedido();
    this.pedidosService.resetarPedidoEdicao();
  }
}