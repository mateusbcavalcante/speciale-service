import { Component, OnInit, Input } from '@angular/core';
import { Produto } from 'src/app/core/domain';

@Component({
  selector: 'app-qtde-produto',
  templateUrl: './qtde-produto.component.html',
  styleUrls: ['./qtde-produto.component.scss'],
})
export class QtdeProdutoComponent implements OnInit {
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
