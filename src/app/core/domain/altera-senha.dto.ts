
export class AlteraSenhaDto {
    idUsuario: number;
    senha: string;
    senhaConfirmacao: string;

    constructor(data?: any) {
        if (data) {
            this.idUsuario = data.idUsuario;
            this.senha = data.senha;
            this.senhaConfirmacao = data.senhaConfirmacao;
        }
    }
}
