import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NavController, ModalController } from '@ionic/angular';
import { StorageService, AuthService } from '../core';
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

  onLogin() {

    this.authService.login(this.loginForm.value).subscribe(
      usuario => {
        this.storageService.setJson('usuario', usuario);
        this.navCtrl.navigateRoot('/tabs');
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
