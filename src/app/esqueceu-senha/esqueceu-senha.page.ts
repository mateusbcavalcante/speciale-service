import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoadingController, ModalController } from '@ionic/angular';
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
    private loadingCtrl: LoadingController,
    private modalCtrl: ModalController,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.esqueceuSenhaForm = this.formBuilder.group({
      email: ['', Validators.compose([Validators.email, Validators.required])]
    });
  }

  async close() {
    await this.modalCtrl.dismiss();
  }

  async onSend() {

    const loading = await this.loadingCtrl.create({
      message: 'Enviando...'
    });
    await loading.present();
    this.authService.esqueceuSenha(this.esqueceuSenhaForm.value).subscribe(
      async usuario => {
        await loading.dismiss();
        await this.notificacaoService.showInfoToaster(`Um email foi enviado para ${usuario.email}. Acesse para redefinir sua senha`);
        await this.close();
      },
      async error => {
        await this.notificacaoService.showErrorToaster(error.error.message);
        await loading.dismiss();
      }
    );
  }
}
