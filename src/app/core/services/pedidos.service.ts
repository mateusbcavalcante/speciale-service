import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MENSAGENS } from '../../shared/mensagens/mensagens';
import { addDays, getDayNameOfWeekByDate, isDomingo } from '../../shared/utils/date-utils';
import { getInputDateValue } from '../../shared/utils/form-utils';
import { ResponsePedidoAdapter } from '../adapters/response-pedido.adapter';
import { DataEntregaDto } from '../domain/data-entrega.dto';
import { ObterPedidoDto } from '../domain/obter-pedido.dto';
import { initializePedido, Pedido, PedidoStatus } from '../domain/pedido';
import { Produto } from '../domain/produto';
import { CarrinhoStore } from '../store/carrinho.store';
import { PedidoStore } from '../store/pedido.store';
import { ProdutosStore } from '../store/produtos.store';
import { ApiService } from './api.service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class PedidosService {

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private pedidoStore: PedidoStore,
    private carrinhoStore: CarrinhoStore,
    private produtosStore: ProdutosStore,
    private responsePedidoAdapter: ResponsePedidoAdapter) {
  }

  calcularDataEntrega(dataPedido: Date): DataEntregaDto {
    let dataEntrega = addDays(dataPedido, 1);
    if (isDomingo(dataEntrega)) {
      dataEntrega = addDays(dataEntrega, 1);
    }
    return {
      dataEntrega,
      diaSemana: getDayNameOfWeekByDate(dataEntrega)
    };
  }

  cadastrarNovoPedidoForm(formData: any) {
    const pedido = this.montarPedidoParaCadastro(formData);
    if (this.isObservacaoInvalida(pedido.observacao)) {
      throw new Error(MENSAGENS.VALIDACAO_OBS);
    }
    this.cadastrarNovoPedido(pedido);
  }

  cadastrarNovoPedido(pedido: Pedido) {
    this.pedidoStore.setStatusEnviandoCriar();
    this.apiService.post<Pedido>(`/pedidos`, pedido).subscribe(
      data => {
        this.produtosStore.restoreProdutos();
        this.carrinhoStore.checkoutCarrinho();
        this.pedidoStore.setStatusEnviadoCriar(MENSAGENS.CRIACAO_NOVO_PEDIDO(data));
      },
      error => {
        this.pedidoStore.setStatusNaoEnviadoCriar();
        throw error;
      }
    );
  }

  obterPedidoStatusCriar(): Observable<PedidoStatus> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.statusCriar)
      );
  }

  setPedidoStatusNaoEnviadoCriar() {
    this.pedidoStore.setStatusNaoEnviadoCriar();
  }

  atualizarPedidoForm(formData: any) {
    const pedido = this.montarPedido(formData);
    if (this.isObservacaoInvalida(pedido.observacao)) {
      throw new Error(MENSAGENS.VALIDACAO_OBS);
    }
    this.atualizarPedido(pedido);
  }

  atualizarPedido(pedido: Pedido) {
    this.pedidoStore.setStatusEnviandoAlterar();
    this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}`, pedido)
      .subscribe(
        data => {
          const pedidoAlterado = this.responsePedidoAdapter.adapt(data);
          this.pedidoStore.carregarPedido(pedidoAlterado);
          this.pedidoStore.carregarPedidoEdicao(pedidoAlterado);
          this.pedidoStore.setStatusEnviadoAlterar();
        },
        error => {
          this.pedidoStore.setStatusNaoEnviadoAlterar();
          throw error;
        }
      );
  }

  isFiltroPesquisaValido(obterPedidoDto: ObterPedidoDto): boolean {
    return !!obterPedidoDto.dataPedido || !!obterPedidoDto.idPedido;
  }

  pesquisarPedido(obterPedidoDto: ObterPedidoDto) {
    const idCliente = this.authService.getUsuarioLogado().idCliente;
    const dataPedido = getInputDateValue(obterPedidoDto.dataPedido);
    const idPedido = !!obterPedidoDto.idPedido ? obterPedidoDto.idPedido : 0;
    this.apiService.get<Pedido>(`/pedidos?idCliente=${idCliente}&idPedido=${idPedido}&dataPedido=${dataPedido}`)
      .subscribe(
        data => this.pedidoStore.carregarPedido(this.responsePedidoAdapter.adapt(data)),
        error => {
          this.pedidoStore.carregarPedido(initializePedido());
          throw error;
        }
      );
  }

  inativarPedido(pedido: Pedido) {
    this.apiService.put<Pedido>(`/pedidos/${pedido.idPedido}/inativar`, pedido)
      .subscribe(data => this.pedidoStore.carregarPedido(initializePedido()));
  }

  removerProdutoPedido(produto: Produto) {
    produto.qtdSolicitada = 0;
    if (produto.idPedidoProduto) {
      produto.flgAtivo = 'N';
    } else {
      this.pedidoStore.removerProdutoPedido(produto);
    }
    this.pedidoStore.addProduto(produto);
  }

  addProdutoPedido(produto: Produto) {
    this.pedidoStore.addProdutoPedido(produto);
    this.pedidoStore.removerProduto(produto);
  }

  obterPedido(): Observable<Pedido> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.pedido)
      );
  }

  carregarPedidoEdicao(pedido: Pedido) {
    this.pedidoStore.carregarPedidoEdicao(_.cloneDeep(pedido));
    this.pedidoStore.setStatusCarregadoAlterar();
  }

  obterPedidoStatusAlterar(): Observable<PedidoStatus> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.statusAlterar)
      );
  }

  getPedidoEdicaoValue(): Pedido {
    return this.pedidoStore.state.pedidoEdicao;
  }

  obterPedidoEdicao(): Observable<Pedido> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.pedidoEdicao)
      );
  }

  obterProdutos(): Observable<Produto[]> {
    return this.pedidoStore.state$
      .pipe(
        map(pedidoState => pedidoState.produtos)
      );
  }

  listarProdutosPorCliente(idCliente?: number) {
    this.apiService.get<Produto[]>(`/produtos/clientes/${idCliente}`)
      .subscribe(
        (produtos: Produto[]) => this.pedidoStore.carregarProdutos(produtos)
      );
  }

  montarPedidoParaCadastro(data: any): Pedido {
    const pedido: Pedido = _.cloneDeep(data);
    pedido.observacao = this.montarObservacaoNovoPedido(pedido);
    pedido.produtos = this.carrinhoStore.state.produtos;
    return pedido;
  }

  private montarObservacaoNovoPedido(pedido: Pedido): string {
    let observacoes = !!pedido.observacao ? `OBSERVAÇÃO GERAL: ${pedido.observacao}\n` : '';
    this.carrinhoStore.state.itens.forEach(
      item => {
        if (item.observacao) {
          observacoes += `${item.produto.desProduto}: ${item.observacao}\n`;
        }
      });
    return observacoes;
  }

  montarPedido(data: any): Pedido {
    const pedido: Pedido = data;
    pedido.produtos = this.pedidoStore.state.pedidoEdicao.produtos;
    return pedido;
  }

  isObservacaoInvalida(obs: string): boolean {
    return (obs && obs.length > 400);
  }

}
