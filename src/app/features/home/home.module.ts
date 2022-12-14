import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePage } from './home.page';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: HomePage,
    children: [
      {
        path: 'produtos',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../produtos/produtos.module').then(m => m.ProdutosPageModule)
          }
        ]
      },
      {
        path: 'carrinho',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../carrinho/carrinho.module').then(m => m.CarrinhoPageModule)
          }
        ]
      },
      {
        path: 'pedidos',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../pedidos/pedidos.module').then(m => m.PedidosPageModule)
          },
          {
            path: ':id',
            loadChildren: () =>
              import('../pedido/pedido.module').then(m => m.PedidoPageModule)
          }
        ]
      },
      {
        path: 'perfil',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../perfil/perfil.module').then(m => m.PerfilPageModule)
          },
          {
            path: 'alterar-senha',
            children: [
              {
                path: '',
                loadChildren: () =>
                  import('../alterar-senha/alterar-senha.module').then(m => m.AlterarSenhaPageModule)
              }
            ]
          },
        ]
      },
      {
        path: 'cliente-pesquisar',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../cliente-pesquisar/cliente-pesquisar.module').then(m => m.ClientePesquisarPageModule)
          }
        ]
      },
      {
        path: '',
        redirectTo: '/app/produtos',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    HomePage
  ]
})
export class HomePageModule {}
