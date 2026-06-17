import { Injectable, NgZone } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject } from 'rxjs'; // IMPORTANTE: Importar o BehaviorSubject

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  public stompClient: Client;
  
  // 1. Em vez de uma array normal, criamos o Mensageiro!
  public grade$ = new BehaviorSubject<number[][]>([]); 

  constructor(private zone: NgZone) {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/tetris-websocket'),
      debug: (str) => { /* console.log(str); (pode comentar para limpar o log) */ }
    });

    this.stompClient.onConnect = (frame) => {
  console.log('Conectado ao Spring com sucesso!');
  
  this.stompClient.subscribe('/topico/tabuleiro', (message) => {
    try {
      const dados = JSON.parse(message.body);
      
      // 💡 DICA DE OURO: Abre o F12 no navegador para ver exatamente o que o Java está mandando!
      console.log('=== DADOS RECEBIDOS DO JAVA ===', dados);
      
      this.zone.run(() => {
        // CASO 1: O Java esteja mandando a matriz pura direta: [[0,0...], [...]]
        if (Array.isArray(dados)) {
          this.grade$.next(dados);
        } 
        // CASO 2: O Java esteja mandando dentro de um Map/Objeto
        else if (dados && typeof dados === 'object') {
          // Procura por qualquer nome que vocês possam ter usado no Map do Java
          const matrizReal = dados.grade || dados.tabuleiro || dados.matriz || dados.tabuleiro?.grade;
          
          if (matrizReal && Array.isArray(matrizReal)) {
            this.grade$.next(matrizReal);
          } else {
            console.warn('Recebemos o objeto do Java, mas a matriz não estava em nenhuma chave conhecida.', Object.keys(dados));
          }
        }
      });

    } catch (erro) {
      console.error('Erro ao processar dados do tabuleiro:', erro);
    }
  });
};

    this.stompClient.activate();
  }

  iniciarJogo() {
    if (this.stompClient.connected) {
      this.stompClient.publish({ destination: '/app/iniciar' });
    }
  }

  enviarMovimento(direcao: string) {
    if (this.stompClient.connected) {
      this.stompClient.publish({ destination: '/app/mover', body: direcao });
    }
  }
}