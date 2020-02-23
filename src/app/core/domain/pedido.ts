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
