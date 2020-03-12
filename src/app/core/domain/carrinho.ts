import { Item } from './item';
import { Produto } from './produto';

export interface Carrinho {

    itens: Item[];
}

export class CarrinhoState {

    itens: Item[] = [];
    produtos: Produto[] = [];

}
