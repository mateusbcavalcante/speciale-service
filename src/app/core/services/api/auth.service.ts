import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NavController } from '@ionic/angular';
import { ApiService } from './api.service';
import { StorageService } from '../utils';
import { LoginDto, Usuario } from '../../domain';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(
    private apiService: ApiService,
    private navCtrl: NavController,
    private storageService: StorageService) {
  }

  login(login: LoginDto): Observable<Usuario> {
    return this.apiService.post<Usuario>(`/seguranca/login`, login);
  }

  esqueceuSenha(formData: any): Observable<Usuario> {
    return this.apiService.post<Usuario>(`/seguranca/recuperarSenha`, formData);
  }

  getUsuarioLogado(): Usuario {
    return this.storageService.getJson('usuario');
  }

  logout(): void {
    this.storageService.clear();
    this.navCtrl.navigateRoot('/');
  }

}
