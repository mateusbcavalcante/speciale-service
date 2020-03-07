import { Component } from '@angular/core';
import { Platform, NavController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
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
      await this.openFirstPage();
    });

  }

  async openFirstPage() {
    if (this.isSessaoAtiva()) {
      await this.navCtrl.navigateRoot('/app');

    } else {
      await this.navCtrl.navigateRoot('/');
    }
  }

  isSessaoAtiva(): boolean {
    return !!this.authService.getUsuarioLogado();
  }

}
