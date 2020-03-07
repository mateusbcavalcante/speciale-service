import { Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root',
})
export class AlertService {

  constructor(
    private alertController: AlertController,
  ) { }


  async confirm(headerText: string, messageText: string, callbackOK: () => void) {
    const alert = await this.alertController.create({
      header: headerText,
      message: messageText.replace(/\n/g, '<br>'),
      mode: 'ios',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel'
        }, {
          text: 'OK',
          handler: callbackOK
        }
      ]
    });

    await alert.present();
  }

}
