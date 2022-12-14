import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Produto } from '../../core/domain/produto';
import { NavParams, ModalController } from '@ionic/angular';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-lista-produtos',
  templateUrl: 'lista-produtos.component.html'
})
export class ListaProdutosComponent implements OnInit {

  @Input()
  produtos: Observable<Produto[]>;

  constructor(
    private modalCtrl: ModalController,
    private navParams: NavParams
    ) {
    this.produtos = this.navParams.get('produtos');
  }

  ngOnInit() {
  }

  async fechar() {
    await this.modalCtrl.dismiss();
  }

  async selecionarProdutoEvent(event: any) {
    await this.modalCtrl.dismiss(event.produto);
  }

}
