import { Produto } from '../../core/domain/produto';
import { Usuario } from '../../core/domain/usuario';
import { Pedido } from '../../core/domain/pedido';

export const MENSAGENS = {

    SENHA_ALTERADA_OK : 'Sua senha foi alterada com sucesso!',

    CONFIMAR_REMOVER_ITEM_CARRINHO : (produto: Produto): string => {
        return `Deseja remover o item ${produto.desProduto} ?`;
    },

    CONFIMAR_REMOVER_PRODUTO : (produto: Produto): string => {
        return `Deseja remover o produto ${produto.desProduto} ?`;
    },

    REDEFINICAO_SENHA: (usuario: Usuario) => {
        return `Um link para redefinição de senha foi enviado para o e-mail ${usuario.email}.`;
    },

    INATIVAR_PEDIDO: (pedido: Pedido) => {
        return `Deseja realmente inativar o pedido ${pedido.idPedido} ?`;
    },

    CRIACAO_NOVO_PEDIDO: (pedido: Pedido) => {
        return `Seu pedido de protocolo ${pedido.idPedido} foi enviado para Speciale com sucesso!`;
    },

    VALIDACAO_OBS: 'As observações não podem conter mais do que 400 caracteres. Você precisa reduzir o tamanhho desse(s) campo(s)',

}
