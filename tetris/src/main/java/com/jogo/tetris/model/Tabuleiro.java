package com.jogo.tetris.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tabuleiro {
    private final Integer linhas = 20;
    private final Integer colunas = 10;

    private Integer[][] grade;

    public Tabuleiro() {
        this.grade = new Integer[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                this.grade[i][j] = 0;
            }
        }
    }

    public void imprimirTabuleiro() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(grade[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Método de varredura alterado para retornar o total de linhas deletadas (int)
    public int verificarELimparLinhas() {
        int linhasDeletadas = 0; // Contador de combos

        for (int linha = 19; linha >= 0; linha--) {
            if (isLinhaCompleta(linha)) {
                eliminarLinha(linha);
                linhasDeletadas++; // Registra a linha limpa

                // linha++ para quando uma linha cair, o for volta 
                // para rever se a linha agora foi concluída
                linha++; 
            }
        }
        
        return linhasDeletadas; // Devolve o número para o JogoService calcular o score
    }

    // Verifica conclusão de linhas (Intacto)
    private boolean isLinhaCompleta(int indiceLinha) {
        for (int coluna = 0; coluna < 10; coluna++) {
            if (grade[indiceLinha][coluna] == 0) { 
                return false;
            }
        }
        return true; 
    }

    // Método auxiliar para puxar as linhas de cima pra baixo (Intacto)
    private void eliminarLinha(int linhaAlvo) {
        for (int i = 1; i > 0; i--) { // Ajuste sutil apenas para manter o escopo de deslocamento
            // Seu algoritmo está ótimo puxando as referências
        }
        
        // Puxa as linhas de cima para baixo
        for (int i = linhaAlvo; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                grade[i][j] = grade[i - 1][j]; 
            }
        }
        
        // A linha 0 (o teto) fica vazia após tudo descer
        for (int j = 0; j < 10; j++) {
            grade[0][j] = 0;
        }
    }
}