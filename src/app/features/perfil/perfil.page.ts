import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../core/domain/usuario';
import { AuthService } from '../../core/services/auth.service';
import { NavController } from '@ionic/angular';
import { Cliente } from 'src/app/core/domain/cliente';
import { Observable } from 'rxjs';
import { ClientesService } from 'src/app/core/services/clientes.service';

@Component({
  selector: 'app-perfil',
  templateUrl: 'perfil.page.html',
  styleUrls: ['perfil.page.scss']
})
export class PerfilPage implements OnInit {

  cliente$: Observable<Cliente>;
  usuario: Usuario;

  constructor(
    private navCtrl: NavController,
    private authService: AuthService,
    private clientesService: ClientesService
  ) { }

  ngOnInit() {
    this.usuario = this.authService.getUsuarioLogado();
    this.cliente$ = this.clientesService.obterCliente();
  }

  logout() {
    this.authService.logout();
    this.navCtrl.navigateRoot('/');
  }

  showAlterarSenha() {
    this.navCtrl.navigateForward('/app/perfil/alterar-senha');
  }

}
