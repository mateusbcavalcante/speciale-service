import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core';
import { Usuario } from '../core/domain';

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
