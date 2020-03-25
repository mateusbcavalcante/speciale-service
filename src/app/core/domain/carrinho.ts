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
    status = CarrinhoStatus.EMPTY;

}
