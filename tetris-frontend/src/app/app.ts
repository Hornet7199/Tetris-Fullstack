import { Component } from '@angular/core';
import { WebsocketService } from './websocket';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [],
  templateUrl: './app.html',
  styleUrl: './app.scss' 
})
export class App {
  
  // O serviço agora é 'public' para o HTML conseguir enxergar a matriz
  constructor(public wsService: WebsocketService) {}

  testarBotao() {
    console.log('Botão clicado! Enviando sinal para o Spring...');
    this.wsService.iniciarJogo();
  }
}