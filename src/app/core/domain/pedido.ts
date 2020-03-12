import { Produto } from './produto';
import { Usuario } from './usuario';

export interface Pedido {

    idCliente: number;
    idUsuario: number;
    idPedido: number;
    dataPedido: Date;
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
        dataPedido: null,
        observacao: '',
    };
}

export class PedidoState {

    pedido: Pedido = initializePedido();
    pedidoEdicao: Pedido = initializePedido();
    produtos: Produto[] = [];
}
