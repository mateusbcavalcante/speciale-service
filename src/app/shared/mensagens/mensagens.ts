import { Produto } from '../../core/domain/produto';
import { Usuario } from '../../core/domain/usuario';
import { Pedido } from '../../core/domain/pedido';
import { Cliente } from 'src/app/core/domain/cliente';

export const MENSAGENS = {

    SENHA_ALTERADA_OK: 'Sua senha foi alterada com sucesso!',

    ACESSO_NAO_PERMITIDO: 'Você não tem permissão para acessar o aplicativo!',

    CONFIMAR_REMOVER_ITEM_CARRINHO: (produto: Produto): string => {
        return `Deseja remover o item ${produto.desProduto} ?`;
    },

    CONFIMAR_REMOVER_PRODUTO: (produto: Produto): string => {
        return `Deseja remover o produto ${produto.desProduto} ?`;
    },

    REDEFINICAO_SENHA: (usuario: Usuario) => {
        return `Um link para redefinição de senha foi enviado para o e-mail ${usuario.email}.`;
    },

    INATIVAR_PEDIDO: (pedido: Pedido) => {
        return `Deseja realmente cancelar o pedido ${pedido.idPedido} ?`;
    },

    CRIACAO_NOVO_PEDIDO: (pedido: Pedido) => {
        return `Seu pedido de protocolo ${pedido.idPedido} foi enviado para Go Bread com sucesso!`;
    },

    VALIDACAO_OBS: 'As observações não podem conter mais do que 400 caracteres. Você precisa reduzir o tamanhho desse(s) campo(s)',

    PESQUISA_PEDIDO_VALIDACAO: 'Pelo menos um dos campos com * é obrigatório.',

    PESQUISA_CLIENTE_VALIDACAO: 'É necessário informar pelo menos 3 caracteres para o Nome do Cliente!',

    CLIENTE_SELECIONADO: (cliente: Cliente) => {
        return `O cliente ${cliente.nomeCliente} foi selecionado com sucesso!`;
    }

};
