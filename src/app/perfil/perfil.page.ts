import { Component, OnInit } from '@angular/core';
import { AuthService, Usuario } from '../core';

@Component({
  selector: 'app-perfil',
  templateUrl: 'perfil.page.html',
  styleUrls: ['perfil.page.scss']
})
export class PerfilPage implements OnInit {

  usuario: Usuario;

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.usuario = this.authService.getUsuarioLogado();
  }

  logout() {
    this.authService.logout();
  }

}
