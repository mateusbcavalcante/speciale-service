import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { DataEntregaDto } from '../../core/domain/data-entrega.dto';
import { PedidosService } from '../../core/services/pedidos.service';

@Component({
  selector: 'app-data-entrega',
  templateUrl: 'data-entrega.component.html',
  styleUrls: ['data-entrega.component.scss']
})
export class DataEntregaComponent implements OnInit {

  @Input()
  dataPedido: FormControl;

  dto: DataEntregaDto;

  constructor(
    private readonly pedidoService: PedidosService
  ) { }

  ngOnInit() {
    this.calcularDataEntrega(this.dataPedido.value);
    this.bindValueChanges();
  }

  bindValueChanges(): void {
    this.dataPedido.valueChanges.subscribe(
      val => {
        if (val) {
          this.calcularDataEntrega(val);
        }
      }
    );
  }

  calcularDataEntrega(dataPedido: Date): void {
    this.dto = this.pedidoService.calcularDataEntrega(dataPedido);
  }

}
