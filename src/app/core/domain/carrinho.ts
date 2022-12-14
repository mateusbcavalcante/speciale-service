import { Item } from './item';
import { Produto } from './produto';

export interface Carrinho {
    itens: Item[];
}
export enum CarrinhoStatus {
    EMPTY = 'EMPTY',
    ADD = 'ADD',
    REMOVER = 'REMOVE',
    CHECKOUT = 'CHECKOUT',
    ERROR = 'ERROR'
}

export class CarrinhoState {

    itens: Item[] = [];
    produtos: Produto[] = [];
    subTotal: Number = 0;
    desconto: Number = 0;
    total: Number = 0;
    status = CarrinhoStatus.EMPTY;

}
