import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NavController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { NotificacaoService } from 'src/app/shared/notificacao/notificacao.service';

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
    private notificacaoService: NotificacaoService
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
      usuario => this.navCtrl.navigateRoot('/app')
    );
  }

  esqueceuSenha() {
    this.authService.esqueceuSenha(this.esqueceuSenhaForm.value).subscribe(
      async usuario => {
        await this.notificacaoService.showInfoToaster(
          `Um email foi enviado para ${usuario.email}.
          Acesse esse email para resdefinir sua senha.`);
      }
    );
  }

}
