
export class ObterClienteDto {

    nomeCliente: String;

    constructor(data?: any) {
        if (data) {
            this.nomeCliente = data.nomeCliente;
        }
    }
}
