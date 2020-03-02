import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-contador-item',
  templateUrl: 'contador-item.component.html',
  styleUrls: [
    'contador-item.component.scss'
  ]
})
export class ContadorItemComponent implements OnInit {

  @Input() item = {
    qtdSolicitada: 0,
    qtdLoteMinimo: 0,
    qtdMultiplo: 0
  };

  constructor() { }

  ngOnInit() {
    if (!this.item.qtdSolicitada) {
      this.item.qtdSolicitada = 0;
    }
  }

  remove() {
    if (this.item.qtdSolicitada === this.item.qtdLoteMinimo) {
      this.item.qtdSolicitada = 0;
    } else if (this.item.qtdSolicitada > 0) {
      this.item.qtdSolicitada -= this.item.qtdMultiplo;
    }
  }

  add() {
    if (this.item.qtdSolicitada === 0) {
      this.item.qtdSolicitada = this.item.qtdLoteMinimo;
    } else {
      this.item.qtdSolicitada += this.item.qtdMultiplo;
    }
  }

}
