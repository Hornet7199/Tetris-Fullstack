import { Injectable, NgZone } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject } from 'rxjs'; 

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  public stompClient: Client;
  
  // 1. O Mensageiro agora guarda o Objeto Completo (FilaDto) em vez de apenas a matriz numérica
  public dadosJogo$ = new BehaviorSubject<any>(null); 

  constructor(private zone: NgZone) {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/tetris-websocket'),
      debug: (str) => { /* console.log(str); */ }
    });

    this.stompClient.onConnect = (frame) => {
      console.log('Conectado ao Spring com sucesso!');
      
      this.stompClient.subscribe('/topico/tabuleiro', (message) => {
        try {
          const dados = JSON.parse(message.body);
          
          console.log('=== DADOS RECEBIDOS DO JAVA (DTO) ===', dados);
          
          this.zone.run(() => {
            // Se recebemos o objeto envelopado com sucesso, repassamos ele completo adiante!
            if (dados && typeof dados === 'object') {
              this.dadosJogo$.next(dados);
            }
          });

        } catch (erro) {
          console.error('Erro ao processar dados do DTO do jogo:', erro);
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
      this.stompClient.publish({ destination: '/app/mover', body: JSON.stringify(direcao) });
    }
  }

  reiniciarJogo() {
    if (this.stompClient.connected) {
      this.stompClient.publish({ destination: '/app/reiniciar' });
    }
  }

  pararJogo() {
    if (this.stompClient.connected) {
      this.stompClient.publish({ destination: '/app/parar' });
    }
  }
}