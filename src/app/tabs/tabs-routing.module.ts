import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
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
          }
        ]
      },
      {
        path: '',
        redirectTo: '/tabs/produtos',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TabsPageRoutingModule {}
