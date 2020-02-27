import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { StorageService } from '../../shared/storage/storage.service';
import { Usuario } from '../domain/usuario';
import { LoginDto } from '../domain/login.dto';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(
    private apiService: ApiService,
    private storageService: StorageService) {
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

  getUsuarioLogado(): Usuario {
    return this.storageService.getJson('usuario');
  }

  logout(): void {
    this.storageService.clear();
  }

}