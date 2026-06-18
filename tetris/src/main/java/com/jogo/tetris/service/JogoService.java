package com.jogo.tetris.service;

import org.springframework.stereotype.Service;
import com.jogo.tetris.model.Peca;
import com.jogo.tetris.model.PecaFila;
import com.jogo.tetris.model.Tabuleiro;

@Service
public class JogoService {
    
    private Tabuleiro tabuleiro;
    private PecaFila fila;
    private Peca pecaAtual;

    public Tabuleiro iniciarNovoJogo() {
        this.tabuleiro = new Tabuleiro();
        this.fila = new PecaFila();
        this.fila.preencherBag();
        
        this.pecaAtual = fila.dequeue();
        System.out.println("Partida iniciada! Primeira peça: " + pecaAtual);
        
        return this.tabuleiro;
    }

    public void processarGravidade() {
        // Lógica de descer a peçaAtual no tabuleiro.
        // Se bater no fundo:
        // 1. tabuleiro.congelarPeca(pecaAtual);
        // 2. tabuleiro.verificarELimparLinhas();
        // 3. pecaAtual = fila.dequeue();
    }
}