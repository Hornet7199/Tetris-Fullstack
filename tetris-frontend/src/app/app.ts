import { Component } from '@angular/core';
import { WebsocketService } from './websocket';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
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