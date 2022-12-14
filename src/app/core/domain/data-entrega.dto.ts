
export class DataEntregaDto {

    diaSemana: string;
    dataEntrega: Date;

    constructor(data?: any) {
        if (data) {
            this.diaSemana = data.diaSemana;
            this.dataEntrega = data.dataEntrega;
        }
    }
}
