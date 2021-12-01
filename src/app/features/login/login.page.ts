import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NavController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { MENSAGENS } from '../../shared/mensagens/mensagens';
import { ClientesService } from 'src/app/core/services/clientes.service';
import { Cliente } from 'src/app/core/domain/cliente';
import { Usuario } from 'src/app/core/domain/usuario';

@Component({
  selector: 'app-login',
  templateUrl: 'login.page.html',
  styleUrls: ['login.page.scss']
})
export class LoginPage implements OnInit {

  formIndex = 0;

  loginForm: FormGroup;
  esqueceuSenhaForm: FormGroup;

  constructor(
    private navCtrl: NavController,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private notificacaoService: NotificacaoService,
    private clientesService: ClientesService
  ) { }

  ngOnInit(): void {
    this.criarFormLogin();
    this.criarFormEsqueceuSenha();
  }

  showForm(index: number) {
    this.formIndex = index;
  }

  criarFormLogin() {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],
      senha: ['', Validators.required],
    });
  }

  criarFormEsqueceuSenha() {
    this.esqueceuSenhaForm = this.formBuilder.group({
      email: ['', Validators.compose([Validators.email, Validators.required])]
    });
  }

  login() {
    this.authService.login(this.loginForm.value).subscribe(
      usuario => {
        if (this.authService.isCliente()) {
          const cliente = this.montarCliente(usuario);
          this.clientesService.setCliente(cliente);
          this.navCtrl.navigateRoot('/app');
        } else if (this.authService.isAdmin()) {
          this.authService.logout();
          this.notificacaoService.showInfoToaster(MENSAGENS.ACESSO_NAO_PERMITIDO);
          return;
        }
      }
    );
  }

  montarCliente(usuario: any): Cliente {
    return {
      idCliente: usuario.idCliente,
      idExternoOmie: usuario.idExternoOmie,
      idTabelaPrecoOmie: usuario.idTabelaPrecoOmie,
      nomeCliente: "",
      cadastroIncompleto: false
    };
  }

  esqueceuSenha() {
    this.authService.esqueceuSenha(this.esqueceuSenhaForm.value).subscribe(
      async usuario => {
        await this.notificacaoService.showSuccessToaster(MENSAGENS.REDEFINICAO_SENHA(usuario));
      }
    );
  }

}
