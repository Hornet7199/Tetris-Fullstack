import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./menu/menu').then(m => m.Menu)
  }
];
