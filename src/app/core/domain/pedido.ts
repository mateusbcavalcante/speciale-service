import { Produto } from './produto';
import { Usuario } from './usuario';

export interface Pedido {

    idCliente: number;
    idUsuario: number;
    idPedido: number;
    dataPedido: Date;
    idOpcaoEntrega: number;
    observacao: string;
    flgAtivo?: string;
    produtos?: Produto[];
    usuarioCadastro?: Usuario;
}

export function initializePedido(): Pedido {
    return {
        idCliente: 0,
        idUsuario: 0,
        idPedido: 0,
        idOpcaoEntrega: 0,
        dataPedido: null,
        observacao: '',
    };
}

export enum PedidoTipoStatus {
    NAO_CARREGADO = 'NAO CARREGADO',
    CARREGADO = 'CARREGADO',
    ENVIANDO = 'ENVIANDO',
    ENVIADO = 'ENVIADO',
    NAO_ENVIADO = 'NAO ENVIADO'
}

export class PedidoStatus {

    tipo = PedidoTipoStatus.NAO_CARREGADO;
    mensagem = '';

    constructor(tipo?: PedidoTipoStatus, mensagem?: string) {
        if (tipo) {
            this.tipo = tipo;
        }
        if (mensagem) {
            this.mensagem = mensagem;
        }
    }
}

export class PedidoState {

    pedido: Pedido = initializePedido();
    pedidoEdicao: Pedido = initializePedido();
    produtos: Produto[] = [];
    statusCriar = new PedidoStatus();
    statusAlterar = new PedidoStatus();

}
