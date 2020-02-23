import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoadingController, NavController, ModalController } from '@ionic/angular';
import { StorageService, NotificacaoService, AuthService } from '../core';
import { EsqueceuSenhaPage } from '../esqueceu-senha/esqueceu-senha.page';

@Component({
  selector: 'app-login',
  templateUrl: 'login.page.html',
  styleUrls: ['login.page.scss']
})
export class LoginPage implements OnInit {
  loginForm: FormGroup;

  constructor(
    private navCtrl: NavController,
    private formBuilder: FormBuilder,
    private storageService: StorageService,
    private notificacaoService: NotificacaoService,
    private loadingCtrl: LoadingController,
    private authService: AuthService,
    private modalCtrl: ModalController
  ) { }

  ngOnInit(): void {
    this.storageService.clear();

    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],
      senha: ['', Validators.required],
    });
  }

  async onLogin() {

    const loading = await this.loadingCtrl.create({
      message: 'Enviando...'
    });
    await loading.present();
    this.authService.login(this.loginForm.value).subscribe(
      async usuario => {
        await loading.dismiss();
        this.storageService.setJson('usuario', usuario);
        this.navCtrl.navigateRoot('/tabs');
      },
      async error => {
        await this.notificacaoService.showErrorToaster(error.error.message);
        await loading.dismiss();
      }
    );
  }

  async abrirEsqueceuSenha() {
    const modal = await this.modalCtrl.create({
      cssClass: 'modal-esqueceu-senha',
      component: EsqueceuSenhaPage
    });
    return await modal.present();
  }
}
