import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { StorageService } from '../../shared/storage/storage.service';
import { Usuario } from '../domain/usuario';
import { LoginDto } from '../domain/login.dto';
import { tap } from 'rxjs/operators';
import { AlteraSenhaDto } from '../domain/altera-senha.dto';
import { ClientesService } from './clientes.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(
    private apiService: ApiService,
    private storageService: StorageService,
    private clientesService: ClientesService) {
  }

  login(login: LoginDto): Observable<Usuario> {
    return this.apiService.post<Usuario>(`/seguranca/login`, login)
      .pipe(
        tap(usuario => this.storageService.setJson('usuario', usuario))
      );
  }

  esqueceuSenha(formData: any): Observable<Usuario> {
    return this.apiService.post<Usuario>(`/seguranca/recuperarSenha`, formData);
  }

  alterarSenha(alterarSenhaDto: AlteraSenhaDto): Observable<Usuario> {
    return this.apiService.put<Usuario>(`/seguranca/alterarSenha`, alterarSenhaDto);
  }

  isCliente() {
    return this.getUsuarioLogado().idGrupo === 10;
  }

  isAdmin() {
    return this.getUsuarioLogado().idGrupo === 11;
  }

  getUsuarioLogado(): Usuario {
    return this.storageService.getJson('usuario');
  }

  setUsuario(usuario: Usuario) {
    this.storageService.setJson('usuario', usuario);
  }

  logout(): void {
    this.storageService.clear();
    this.clientesService.resetarCliente();
  }

}
