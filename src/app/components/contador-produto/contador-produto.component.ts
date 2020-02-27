import { Component, OnInit, Input } from '@angular/core';
import { Produto } from '../../core/domain/produto';

@Component({
  selector: 'app-contador-produto',
  templateUrl: 'contador-produto.component.html',
  styleUrls:[
    'contador-produto.component.scss'
  ]
})
export class ContadorProdutoComponent implements OnInit {

  @Input() produto: Produto;

  constructor() { }

  ngOnInit() {
    if (!this.produto.qtdSolicitada) {
      this.produto.qtdSolicitada = 0;
    }
  }

  remove() {
    if (this.produto.qtdSolicitada === this.produto.qtdLoteMinimo) {
      this.produto.qtdSolicitada = 0;
    } else if (this.produto.qtdSolicitada > 0) {
      this.produto.qtdSolicitada -= this.produto.qtdMultiplo;
    }
  }

  add() {
    if (this.produto.qtdSolicitada === 0) {
      this.produto.qtdSolicitada = this.produto.qtdLoteMinimo;
    } else {
      this.produto.qtdSolicitada += this.produto.qtdMultiplo;
    }
  }

}
