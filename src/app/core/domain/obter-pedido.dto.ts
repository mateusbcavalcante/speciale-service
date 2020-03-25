
export class ObterPedidoDto {

    dataPedido: Date;
    idCliente: number;
    idPedido: number;

    constructor(data?: any) {
        if (data) {
            this.dataPedido = data.dataPedido;
            this.idCliente = data.idCliente;
            this.idCliente = data.idPedido;
        }
    }
}
