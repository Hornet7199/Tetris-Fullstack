package com.jogo.tetris.dto;

import com.jogo.tetris.model.Tabuleiro;
import java.util.List;

public class FilaDto {
    private Tabuleiro tabuleiro;
    private List<int[][]> proximasPecas; 
    private boolean jogoAtivo;
    private int pontuacao;         // Novo campo para o painel de PONTOS
    private int linesEliminadas;   // Novo campo para o painel de LINHAS

    // Construtor padrão
    public FilaDto() {}

    // Construtor completo atualizado para facilitar a criação no Service
    public FilaDto(Tabuleiro tabuleiro, List<int[][]> proximasPecas, boolean jogoAtivo, int pontuacao, int linesEliminadas) {
        this.tabuleiro = tabuleiro;
        this.proximasPecas = proximasPecas;
        this.jogoAtivo = jogoAtivo;
        this.pontuacao = pontuacao;
        this.linesEliminadas = linesEliminadas;
    }

    // Getters e Setters (sem lombok aqui)
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public void setTabuleiro(Tabuleiro tabuleiro) { this.tabuleiro = tabuleiro; }

    public List<int[][]> getProximasPecas() { return proximasPecas; }
    public void setProximasPecas(List<int[][]> proximasPecas) { this.proximasPecas = proximasPecas; }

    public boolean isJogoAtivo() { return jogoAtivo; }
    public void setJogoAtivo(boolean jogoAtivo) { this.jogoAtivo = jogoAtivo; }

    // Novos Getters e Setters para a pontuação e linhas
    public int getPontuacao() { return pontuacao; }
    public void setPontuacao(int pontuacao) { this.pontuacao = pontuacao; }

    public int getLinesEliminadas() { return linesEliminadas; }
    public void setLinesEliminadas(int linesEliminadas) { this.linesEliminadas = linesEliminadas; }
}