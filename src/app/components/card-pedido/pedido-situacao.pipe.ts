import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pedidoSituacao'
})
export class PedidoSituacaoPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'S': return 'ATIVO';
      case 'N': return 'INATIVO';
      default: return value;
    }
  }
}
