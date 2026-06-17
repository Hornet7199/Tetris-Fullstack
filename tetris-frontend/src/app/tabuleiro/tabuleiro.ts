import { Component } from '@angular/core';
import { WebsocketService } from '../websocket';

@Component({
  selector: 'app-tabuleiro',
  imports: [],
  templateUrl: './tabuleiro.html',
  styleUrl: './tabuleiro.scss',
})
export class Tabuleiro {
// O serviço agora é 'public' para o HTML conseguir enxergar a matriz
  constructor(public wsService: WebsocketService) {}

  testarBotao() {
    console.log('Botão clicado! Enviando sinal para o Spring...');
    this.wsService.iniciarJogo();
  }
}
