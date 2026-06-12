import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  public stompClient: Client;
  public grade: number[][] = []; // A gaveta que guarda a matriz para o HTML ver

  constructor() {
    this.stompClient = new Client({
      // Conecta no Java usando o SockJS
      webSocketFactory: () => new SockJS('http://localhost:8080/tetris-websocket'),
      debug: (str) => { console.log(str); }
    });

    // O que fazer assim que o rádio ligar e achar o Java
    this.stompClient.onConnect = (frame) => {
      console.log('Conectado ao Spring com sucesso!', frame);
      
      // Sintoniza na rádio do tabuleiro e fica ouvindo
      this.stompClient.subscribe('/topico/tabuleiro', (message) => {
        const dados = JSON.parse(message.body);
        this.grade = dados.grade; // Salva a matriz que chegou do Java!
        console.log('Matriz salva com sucesso no Angular!');
      });
    };

    // Liga o rádio na tomada
    this.stompClient.activate();
  }

  // O botão da tela vai chamar essa função
  iniciarJogo() {
    if (this.stompClient.connected) {
      this.stompClient.publish({ destination: '/app/iniciar' });
    } else {
      console.error('Opa! O rádio ainda não conectou no Java.');
    }
  }
}