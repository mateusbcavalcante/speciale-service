import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { NotificacaoService, AuthService } from '../core';

@Component({
  selector: 'app-esqueceu-senha',
  templateUrl: 'esqueceu-senha.page.html',
  styleUrls: ['esqueceu-senha.page.scss']
})
export class EsqueceuSenhaPage implements OnInit {
  esqueceuSenhaForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private notificacaoService: NotificacaoService,
    private modalCtrl: ModalController,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.esqueceuSenhaForm = this.formBuilder.group({
      email: ['', Validators.compose([Validators.email, Validators.required])]
    });
  }

  close(message?: any) {
    this.modalCtrl.dismiss().then(
      async () => {
        if (message) {
          await this.notificacaoService.showInfoToaster(message);
        }
      }
    );
  }

  onSend() {

    this.authService.esqueceuSenha(this.esqueceuSenhaForm.value).subscribe(
      usuario => {
        this.close(`Um email foi enviado para ${usuario.email}. Acesse para redefinir sua senha`);
      }
    );
  }
}
