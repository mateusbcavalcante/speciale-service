import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pedidoSituacao'
})
export class PedidoSituacaoPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'S': return 'NÃO';
      case 'N': return 'SIM';
      default: return value;
    }
  }
}
