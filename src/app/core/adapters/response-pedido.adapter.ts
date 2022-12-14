import { Injectable } from '@angular/core';
import { Pedido } from '../domain/pedido';
import { Adapter } from './adapter';

@Injectable({
    providedIn: 'root'
})
export class ResponsePedidoAdapter implements Adapter<Pedido> {

    adapt(data: any): Pedido {
        data.dataPedido = this.formataDataPedido(data.dataPedido);
        return data;
    }

    private formataDataPedido(dataPedido: string): Date {
        return new Date(dataPedido);
    }
}
