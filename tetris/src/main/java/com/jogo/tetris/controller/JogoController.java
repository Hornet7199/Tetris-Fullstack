package com.jogo.tetris.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jogo.tetris.model.Peca;
import com.jogo.tetris.model.PecaFila;
import com.jogo.tetris.model.Tabuleiro;

@Controller
@RequestMapping("/tetris")
public class JogoController {
    /*
     * Anotações Gerais
     * 
     * 
     * Métodos devem ter MessageMapping (de onde pega) e SendTo (Pra quem manda).
     * Em ligação, você recebe e manda a voz ao mesmo tempo
     */

    @MessageMapping("/iniciar")
    @SendTo("/topico/tabuleiro")
    public Tabuleiro iniciarJogo() {

        Tabuleiro tabuleiro = new Tabuleiro();

        PecaFila fila = new PecaFila();
        fila.preencherBag();

        Peca primeiraPeca = fila.dequeue();

        System.out.println("Primeira peça criada!");

        return tabuleiro;
    }

}
