import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
import { Usuario } from '../domain/usuario';
import { LoginDto } from '../domain/login.dto';
import { NavController } from '@ionic/angular';

@Injectable()
export class AuthService {

  constructor(
    private http: HttpClient,
    private navCtrl: NavController,
    private storageService: StorageService) {
  }

  login(login: LoginDto): Observable<Usuario> {
    return this.http.post<Usuario>(`${environment.base_url}/seguranca/login`, login);
  }

  esqueceuSenha(formData: any): Observable<Usuario> {
    return this.http.post<Usuario>(`${environment.base_url}/seguranca/recuperarSenha`, formData);
  }

  getUsuarioLogado(): Usuario {
    return this.storageService.getJson('usuario');
  }

  logout(): void {
    this.storageService.clear();
    this.navCtrl.navigateRoot('/');
  }

}
