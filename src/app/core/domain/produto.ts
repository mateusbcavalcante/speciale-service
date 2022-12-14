
export interface Produto {

    idPedidoProduto?: number;
    idProduto: number;
    desProduto: string;
    qtdLoteMinimo: number;
    qtdMultiplo: number;
    qtdSolicitada: number;
    flgAtivo: string;
    valorUnitario?: number;
}


export class ProdutosState {

    disponiveis: Produto[] = [];
}
