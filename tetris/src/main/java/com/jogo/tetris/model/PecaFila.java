package com.jogo.tetris.model;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import com.jogo.tetris.util.FisherYates;

import java.util.List;

public class PecaFila {
    Peca inicio;
    Peca fim;

    public boolean isEmpty() {
        return inicio == null && fim == null;
    }

    public void enqueue(Peca entrarNaFila) {
        if (isEmpty()) {
            inicio = entrarNaFila;
            fim = entrarNaFila;

        } else {
            fim.proximo = entrarNaFila;
            fim = entrarNaFila;
        }
    }

    public Peca dequeue() {

        if (isEmpty()) {
            return null;
        }

        Peca removida = inicio;

        if (inicio == fim) {
            inicio = null;
            fim = null;
        } else {
            inicio = inicio.proximo;
        }

        removida.proximo = null;

        return removida;
    }

    /**
     * Gera uma bag com as 7 peças e embaralha usando Fisher-Yates
     */
    public void preencherBag() {

        List<Peca> bag = new ArrayList<>();

        bag.add(FabricaPecas.criarI());
        bag.add(FabricaPecas.criarO());
        bag.add(FabricaPecas.criarT());
        bag.add(FabricaPecas.criarS());
        bag.add(FabricaPecas.criarZ());
        bag.add(FabricaPecas.criarJ());
        bag.add(FabricaPecas.criarL());

        FisherYates.embaralhar(bag);

        for (Peca p : bag) {
            enqueue(p);
        }
    }

    @Override
    public PecaFila clone() {
        PecaFila nova = new PecaFila();
        nova.inicio = this.inicio;
        nova.fim = this.fim;
        return nova;
    }

    public String view() {
        if (isEmpty())
            return "";
        PecaFila aux = clone();
        String ret = "";
        do {
            ret += aux.inicio.getMatriz() + "|";
            aux.inicio = aux.inicio.proximo;
        } while (aux.inicio != null);
        return ret;

    }

}
