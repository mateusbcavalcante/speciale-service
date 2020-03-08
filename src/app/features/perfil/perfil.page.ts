import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../core/domain/usuario';
import { AuthService } from '../../core/services/auth.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-perfil',
  templateUrl: 'perfil.page.html',
  styleUrls: ['perfil.page.scss']
})
export class PerfilPage implements OnInit {

  usuario: Usuario;

  constructor(
    private navCtrl: NavController,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.usuario = this.authService.getUsuarioLogado();
  }

  logout() {
    this.authService.logout();
    this.navCtrl.navigateRoot('/');
  }

  showAlterarSenha() {
    this.navCtrl.navigateForward('/app/perfil/alterar-senha');
  }

}
