import { Component } from '@angular/core';
import { Platform, NavController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  template: `<ion-app>
              <ion-router-outlet></ion-router-outlet>
             </ion-app>`
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private authService: AuthService,
    private navCtrl: NavController
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(async () => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      await this.restaurarSessao();
    });

  }

  async restaurarSessao() {
    if (!this.isSessaoAtiva()) {
      await this.navCtrl.navigateRoot('/');
    } else {
      await this.navCtrl.navigateRoot('/app');
    }
  }

  isSessaoAtiva(): boolean {
    return !!this.authService.getUsuarioLogado();
  }

}
