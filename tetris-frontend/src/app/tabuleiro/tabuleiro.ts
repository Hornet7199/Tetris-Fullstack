import { CommonModule } from '@angular/common'; 
import { Component, HostListener, OnInit, ChangeDetectorRef } from '@angular/core';
import { WebsocketService } from '../websocket'; 

@Component({
  selector: 'app-tabuleiro', 
  standalone: true,
  imports: [CommonModule], 
  
  templateUrl: './tabuleiro.html', 
  styleUrl: './tabuleiro.scss'     
})
export class Tabuleiro implements OnInit { 
  
  public grade: number[][] = []; 

  constructor(
    public wsService: WebsocketService,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit() {
    this.wsService.grade$.subscribe(novaGrade => {
      if (novaGrade && novaGrade.length > 0) {
        // Usando o spread operator para forçar a atualização visual da matriz
        this.grade = novaGrade.map(linha => [...linha]);
        this.cdr.detectChanges(); 
      }
    });
  }

  @HostListener('window:keydown', ['$event'])
  escutarTeclado(event: KeyboardEvent) {
    if (event.key === 'ArrowLeft') this.wsService.enviarMovimento('ESQUERDA');
    else if (event.key === 'ArrowRight') this.wsService.enviarMovimento('DIREITA');
    else if (event.key === 'ArrowDown') this.wsService.enviarMovimento('BAIXO');
  }

  testarBotao() {
    this.wsService.iniciarJogo();
  }
}