import { Component } from '@angular/core';
import { WebsocketService } from './websocket';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [],
  templateUrl: './app.html',
  styleUrl: './app.scss' 
})
export class App { // <-- A mudança foi feita nesta linha!
  
  constructor(private wsService: WebsocketService) {}

  testarBotao() {
    console.log('Botão clicado! Enviando sinal para o Spring...');
    this.wsService.iniciarJogo();
  }
}