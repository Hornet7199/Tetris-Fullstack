package com.jogo.tetris.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Peca {

    private int[][] matriz; // Define o formato e a cor dos blocos
    private int x; // Posição X atual no tabuleiro
    private int y; // Posição Y atual no tabuleiro (topo)
    public Peca proximo;

    // Construtor
    public Peca(int[][] matrizInicial) {
        this.matriz = matrizInicial;
        this.x = 3; // Posição padrão de surgimento (meio do tabuleiro)
        this.y = 0; // Começa no topo
    }

    public void moverParaBaixo() {
        this.y++;
    }

    public void moverParaEsquerda() {
        this.x--;
    }

    public void moverParaDireita() {
        this.x++;
    }

    // Método para rotacionar a matriz 90 graus no sentido horário
    public void rotacionar() {
        int largura = matriz.length;
        int[][] novaMatriz = new int[largura][largura];

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < largura; j++) {
                novaMatriz[j][largura - 1 - i] = matriz[i][j];
            }
        }
        this.matriz = novaMatriz;
    }
}
