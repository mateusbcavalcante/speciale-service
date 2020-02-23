
export class LoginDto {
    login: string;
    senha: string;

    constructor(data?: any) {
        if (data) {
            this.login = data.login;
            this.senha = data.senha;
        }
    }
}
