import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pedidoOpcao'
})
export class PedidoOpcaoPipe implements PipeTransform {
  transform(value: number): string {
    switch (value) {
      case 1: return 'Retirada';
      case 2: return 'Entrega';
      default: return 'Não Definido';
    }
  }
}
