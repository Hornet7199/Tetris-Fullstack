import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: Client;

  constructor() {
    // Aponta direto para a porta 8080, onde o Spring está rodando
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/tetris-websocket'),
      debug: (msg) => console.log(msg) // Vai imprimir no F12 tudo o que acontecer
    });

    // O que o Angular deve fazer assim que a conexão der certo
    this.stompClient.onConnect = (frame) => {
      console.log('Conectado ao Spring com sucesso!', frame);
      
      // Inscreve o Angular no canal do tabuleiro para ouvir as respostas do Java
      this.stompClient.subscribe('/topico/tabuleiro', (mensagem) => {
        const tabuleiroRecebido = JSON.parse(mensagem.body);
        console.log('Matriz recebida do Java:', tabuleiroRecebido);
      });
    };

    // Liga o rádio e tenta conectar
    this.stompClient.activate();
  }

  // Função para enviar o comando de iniciar pro Java
  iniciarJogo() {
    this.stompClient.publish({
      destination: '/app/iniciar',
      body: '' 
    });
  }
}