import { Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Injectable()
export class AlertService {

  constructor(
    private alertController: AlertController,
  ) { }


  async confirm(headerText: string, messageText: string, callbackOK: () => void) {
    const alert = await this.alertController.create({
      header: headerText,
      message: messageText,
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
