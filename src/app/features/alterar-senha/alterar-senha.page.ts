import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NotificacaoService } from '../../shared/notificacao/notificacao.service';
import { AuthService } from '../../core/services/auth.service';
import { Usuario } from '../../core/domain/usuario';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-alterar-senha',
  templateUrl: 'alterar-senha.page.html',
  styleUrls: [
    'alterar-senha.page.scss'
  ]
})
export class AlterarSenhaPage implements OnInit {

  usuario: Usuario;

  alterarSenhaForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private navCtrl: NavController,
    private notificacaoService: NotificacaoService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.usuario = this.authService.getUsuarioLogado();
    this.criarFormularioAlterarSenha();
  }

  criarFormularioAlterarSenha() {
    const usuario = this.authService.getUsuarioLogado();
    this.alterarSenhaForm = this.formBuilder.group({
      senha: ['', Validators.compose([
        Validators.required,
        Validators.minLength(6)
      ])],
      senhaConfirmacao: ['', Validators.compose([
        Validators.required,
        Validators.minLength(6),
      ])],
      idUsuario: [usuario.idUsuario]
    }, { validator: this.checkPasswords });
  }

  checkPasswords(group: FormGroup) {
    const pass = group.get('senha').value;
    const confirmPass = group.get('senhaConfirmacao').value;
    return pass === confirmPass ? null : { notSame: true };
  }

  alterarSenha() {
    this.authService.alterarSenha(this.alterarSenhaForm.value).subscribe(
      async data => await this.notificacaoService.showSuccessToaster('Sua senha foi alterada com sucesso !!!')
    );
  }

  voltar() {
    this.navCtrl.navigateRoot('/app/perfil');
  }

}
