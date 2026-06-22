import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./menu/menu').then(m => m.Menu)

    
  },

  {

    path: 'tabuleiro',
    loadComponent: () => import('./tabuleiro/tabuleiro').then(m => m.Tabuleiro)
  },
  {
   path: 'tutorial',
    loadComponent: () => import('./tutorial/tutorial').then(m => m.Tutorial)
  },
{
  path: 'menu',
  loadComponent: () => import('./menu/menu').then(m => m.Menu)
}
 
];
