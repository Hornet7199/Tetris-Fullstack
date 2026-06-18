package com.jogo.tetris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jogo.tetris.model.Tabuleiro;
import com.jogo.tetris.service.JogoService;

@Controller
@RequestMapping("/tetris")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @MessageMapping("/iniciar")
    @SendTo("/topico/tabuleiro")
    public Tabuleiro iniciarJogo() {
        return jogoService.iniciarNovoJogo();
    }
}