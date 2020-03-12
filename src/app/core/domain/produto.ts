
export interface Produto {

    idPedidoProduto?: number;
    idProduto: number;
    desProduto: string;
    qtdLoteMinimo: number;
    qtdMultiplo: number;
    qtdSolicitada: number;
    flgAtivo: string;
}


export class ProdutosState {

    disponiveis: Produto[] = [];
}
