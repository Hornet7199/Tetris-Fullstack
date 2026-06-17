import { CommonModule } from '@angular/common'; 
import { Component, HostListener, OnInit, ChangeDetectorRef } from '@angular/core';
import { WebsocketService } from '../websocket'; 

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule], 
  
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit { 
  
  public grade: number[][] = []; 

  constructor(
    public wsService: WebsocketService,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit() {
    this.wsService.grade$.subscribe(novaGrade => {
      if (novaGrade && novaGrade.length > 0) {
        this.grade = novaGrade;
        this.cdr.detectChanges(); // Força a tela atualizar
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
