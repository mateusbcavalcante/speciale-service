
export interface Produto {

    idProduto: number;
    desProduto: string;
    qtdLoteMinimo: number;
    qtdMultiplo: number;
    qtdSolicitada: number;
    status?: string;
}
