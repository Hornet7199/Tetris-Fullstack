package com.jogo.tetris.util;

import java.util.List;
import java.util.Random;

import com.jogo.tetris.model.Peca;

public class FisherYates {

    public static void embaralhar(List<Peca> lista) {

        Random random = new Random();

        for (int i = lista.size() - 1; i > 0; i--) {

            int j = random.nextInt(i + 1);

            Peca temp = lista.get(i);
            lista.set(i, lista.get(j));
            lista.set(j, temp);
        }
    }
}