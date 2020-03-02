import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ApiService } from './api.service';
import { Produto } from '../domain/produto';

@Injectable({
  providedIn: 'root',
})
export class ProdutosService {

  private _produtos = new BehaviorSubject<Produto[]>([]);
  private _store: { produtos: Produto[] } = { produtos: [] };
  readonly produtos = this._produtos.asObservable();

  constructor(private apiService: ApiService) { }

  listarProdutosCliente(idCliente: number) {
    this.apiService.get<Produto[]>(`/produtos/clientes/${idCliente}`).subscribe(
      data => {
        this._store.produtos = data;
        this._produtos.next(Object.assign({}, this._store).produtos);
      }
    );
  }

}
