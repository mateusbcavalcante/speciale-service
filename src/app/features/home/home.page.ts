import { Component, OnInit } from '@angular/core';
import { CarrinhoService } from '../../core/services/carrinho.service';
import { Carrinho } from '../../core/domain/carrinho';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html'
})
export class HomePage implements OnInit {

  carrinho$: Observable<Carrinho>;

  constructor(private carrinhoService: CarrinhoService) { }

  ngOnInit() {
    this.carrinho$ = this.carrinhoService.carrinho;
  }

}
