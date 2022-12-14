export interface Cliente {

    idCliente: number;
    idExternoOmie: number;
    idTabelaPrecoOmie: number;
    nomeCliente: String;
    cadastroIncompleto: Boolean;

}

export function initializeCliente(): Cliente {
    return {
        idCliente: 0,
        idExternoOmie: 0,
        idTabelaPrecoOmie: 0,
        nomeCliente: '',
        cadastroIncompleto: true
    };
}

export class ClienteState {
    cliente: Cliente = initializeCliente();
}
