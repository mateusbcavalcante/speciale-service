
export class ObterPedidoDto {

    dataPedido: Date;
    idCliente: number;

    constructor(data?: any) {
        if (data) {
            this.dataPedido = data.dataPedido;
            this.idCliente = data.idCliente;
        }
    }
}
