import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core'; // Adicionado o OnInit aqui
import { Router } from '@angular/router';
import { WebsocketService } from '../websocket';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule], // Removido o WebsocketService daqui (ele é um serviço, não um componente/módulo)
  templateUrl: './menu.html',
  styleUrls: ['./menu.scss']
})
export class Menu implements OnInit {

  // Injetamos o Router E o WebsocketService aqui no construtor!
  constructor(
    private router: Router,
    private wsService: WebsocketService 
  ) {}

  ngOnInit() {
    // Agora o "this.wsService" funciona! 
    // Isso avisa o Angular: "Esqueça qualquer dado de jogo que sobrou da partida passada"
    if (this.wsService?.dadosJogo$) {
      this.wsService.dadosJogo$.next(null);
    }
  }

  jogar() {
    this.router.navigate(['/tabuleiro']);
  }

  tutorial() {
    this.router.navigate(['/tutorial']);
  }
}