import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  template:
    `<ion-tabs>
        <ion-tab-bar slot="bottom">
          <ion-tab-button tab="produtos">
            <ion-icon name="list-outline"></ion-icon>
            <ion-label>Produtos</ion-label>
          </ion-tab-button>

          <ion-tab-button tab="carrinho">
            <ion-icon name="cart-outline"></ion-icon>
            <!-- <ion-badge color="primary">11</ion-badge> -->
            <ion-label>Carrinho</ion-label>
          </ion-tab-button>

          <ion-tab-button tab="pedidos">
            <ion-icon name="documents-outline"></ion-icon>
            <ion-label>Pedidos</ion-label>
          </ion-tab-button>

          <ion-tab-button tab="perfil">
            <ion-icon name="person-outline"></ion-icon>
            <ion-label>Perfil</ion-label>
          </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>`
})
export class HomePage {

  constructor() { }

}
