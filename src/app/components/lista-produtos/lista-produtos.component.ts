import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Produto } from '../../core/domain/produto';
import { AlertService } from 'src/app/shared/alertas/alert.service';

@Component({
  selector: 'app-lista-produtos',
  templateUrl: 'lista-produtos.component.html',
  styleUrls: [
    'lista-produtos.component.scss'
  ]
})
export class ListaProdutosComponent implements OnInit {

  @Input() produtos: Produto[];

  @Input() podeRemoverProduto = false;

  @Input() podeAddProduto = false;

  @Output()
  removerProdutoHandler: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  addProdutoHandler: EventEmitter<any> = new EventEmitter<any>();


  constructor(private alertServive: AlertService) { }

  ngOnInit() {

  }

  async removerProdutoEvent(produto: Produto) {
    await this.alertServive.confirm('Confirmação', `Deseja remover do carrinho o produto ${produto.desProduto} ?`, () => {
      this.removerProdutoHandler.emit({
        produto
      });
    });
  }

  addProdutoEvent(produto: Produto) {
    this.addProdutoHandler.emit({
      produto
    });
  }

}
