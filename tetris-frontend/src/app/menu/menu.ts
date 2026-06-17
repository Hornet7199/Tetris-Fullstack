import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
   standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.html',
  styleUrl: './menu.scss',
})
export class Menu {
    constructor(private router: Router) {}

  jogar() {
    this.router.navigate(['/tabuleiro']);
  }
  tutorial(){
    this.router.navigate(['/tutorial'])
  }
}
