package com.jogo.tetris.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @MessageMapping("/iniciar") // tipo PostMapping. Ele recebe coisa do Angular
    @SendTo("/topico/tabuleiro") // Tipo GetMapping. Ele envia coisa pro Angular
    public Tabuleiro iniciarJogo() {
        System.out.println("O Angular pediu para iniciar o jogo! Criando tabuleiro..."); // TESTE, MUDAR
        Tabuleiro novoTabuleiro = new Tabuleiro();
        return novoTabuleiro;
    }

}
