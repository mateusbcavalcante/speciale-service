import { Injectable } from '@angular/core';
import { parseToISODateTime } from '../../shared/utils/date-utils';
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
        return !!dataPedido ? parseToISODateTime(dataPedido) : null;
    }
}
