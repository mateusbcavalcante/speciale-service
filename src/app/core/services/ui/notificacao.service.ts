import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root',
})
export class NotificacaoService {

  private toast: HTMLIonToastElement;

  constructor(
    private toastCtrl: ToastController,
  ) {
  }

  async showToaster(message: string, duration: number, color: string) {

    if (this.toast) {
      await this.toast.dismiss();
    }

    this.toast = await this.toastCtrl.create({
      message,
      color,
      mode: 'ios',
      duration,
      position: 'top',
      buttons: [
        {
          text: 'X',
          role: 'cancel'
        }
      ]
    });
    await this.toast.present();
  }

  async showErrorToaster(message: string, duration = 30000) {
    await this.showToaster(message, duration, 'danger');
  }

  async showSuccessToaster(message: string, duration = 30000) {
    await this.showToaster(message, duration, 'success');
  }

  async showInfoToaster(message: string, duration = 5000) {
    await this.showToaster(message, duration, 'tertiary');
  }

  async showWarningToaster(message: string, duration = 5000) {
    await this.showToaster(message, duration, 'warning');
  }

}
