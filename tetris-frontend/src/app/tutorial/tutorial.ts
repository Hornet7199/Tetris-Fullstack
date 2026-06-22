import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { WebsocketService } from '../websocket';

@Component({
  selector: 'app-tutorial',
   standalone: true,
  imports: [CommonModule],
  templateUrl: './tutorial.html',
  styleUrl: './tutorial.scss',
})
export class Tutorial {
 constructor(
    private router: Router,
    private wsService: WebsocketService 
  ) {}
  menu() {
    this.router.navigate(['/menu']);
  }
}
