import { CommonModule } from '@angular/common'; 
import { Component, HostListener, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { WebsocketService } from '../websocket'; 

@Component({
  selector: 'app-tabuleiro', 
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './tabuleiro.html', 
  styleUrl: './tabuleiro.scss'     
})
export class Tabuleiro implements OnInit { 
  
  public dadosJogo: any = null;
  public jogoIniciado: boolean = false;
  private musicaMusica: HTMLAudioElement | null = null;

  constructor(
    public wsService: WebsocketService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {
    this.musicaMusica = new Audio('audios/tetris-theme.mp3');
    if (this.musicaMusica) {
      this.musicaMusica.loop = true;
      this.musicaMusica.volume = 0.25;
    }
  }

  ngOnInit() {
    this.wsService.dadosJogo$.subscribe(novosDados => {
      if (novosDados) {
        this.dadosJogo = novosDados;
        
        if (!this.jogoIniciado) {
          this.jogoIniciado = true;
        }

        if (this.dadosJogo && !this.dadosJogo.jogoAtivo) {
          this.pararMusica();
        }
        
        this.cdr.detectChanges(); 
      }
    });
  }

  @HostListener('window:keydown', ['$event'])
  escutarTeclado(event: KeyboardEvent) {
    if (!this.jogoIniciado || (this.dadosJogo && !this.dadosJogo.jogoAtivo)) {
      return;
    }

    if (event.key === 'ArrowLeft') this.wsService.enviarMovimento('ESQUERDA');
    else if (event.key === 'ArrowRight') this.wsService.enviarMovimento('DIREITA');
    else if (event.key === 'ArrowDown') this.wsService.enviarMovimento('BAIXO');
    else if (event.key === 'ArrowUp') this.wsService.enviarMovimento('GIRAR');
  }

  comecar() {
    this.jogoIniciado = true;
    this.reproduzirMusica();
    this.wsService.reiniciarJogo();
  }

  reiniciar() {
    this.pararMusica();
    if (this.musicaMusica) {
      this.musicaMusica.currentTime = 0;
    }
    this.reproduzirMusica();
    this.wsService.reiniciarJogo(); 
  }

 voltarAoMenu() {
    this.pararMusica();
    if (this.musicaMusica) {
      this.musicaMusica.currentTime = 0;
    }
    
    this.wsService.pararJogo(); 
    
    this.wsService.dadosJogo$.next(null);
    this.jogoIniciado = false;
    
    this.dadosJogo = null;
    this.router.navigate(['/']); 
  }

  private reproduzirMusica() {
    if (this.musicaMusica && this.musicaMusica.paused) {
      this.musicaMusica.play().catch(err => {
        console.warn("Áudio barrado pelo navegador:", err);
      });
    }
  }

  private pararMusica() {
    if (this.musicaMusica && !this.musicaMusica.paused) {
      this.musicaMusica.pause();
    }
  }
}