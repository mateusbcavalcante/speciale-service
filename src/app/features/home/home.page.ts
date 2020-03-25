import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../../core/domain/item';
import { LojaService } from '../../core/services/loja.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html'
})
export class HomePage implements OnInit {

  itens$: Observable<Item[]>;

  constructor(private lojaService: LojaService) { }

  ngOnInit() {
    this.obterItensCarrinho();
  }

  obterItensCarrinho() {
    this.itens$ = this.lojaService.obterItensCarrinho();
  }

}
