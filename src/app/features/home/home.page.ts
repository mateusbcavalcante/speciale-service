import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from 'src/app/core/domain/cliente';
import { AuthService } from 'src/app/core/services/auth.service';
import { ClientesService } from 'src/app/core/services/clientes.service';
import { Item } from '../../core/domain/item';
import { LojaService } from '../../core/services/loja.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html'
})
export class HomePage implements OnInit {

  itens$: Observable<Item[]>;
  cliente: Cliente;

  constructor(private lojaService: LojaService,
              private authService: AuthService,
              private clientesService: ClientesService) { }

  ngOnInit() {
    this.obterItensCarrinho();
    this.obterCliente();
  }

  obterItensCarrinho() {
    this.itens$ = this.lojaService.obterItensCarrinho();
  }

  obterCliente() {
    this.clientesService.obterCliente().subscribe(data => {
      this.cliente = data;
    });
  }
  
  isCadastroIncompleto() {
    return this.cliente.cadastroIncompleto;
  }

  isUsuarioLogadoCliente() {
    return this.authService.isCliente();
  }

}
