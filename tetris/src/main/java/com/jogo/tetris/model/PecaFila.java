package com.jogo.tetris.model;

public class PecaFila {
    Peca inicio;
    Peca fim;

    public boolean isEmpty() {
        return inicio == null && fim == null;
    }

    public void enqueve(Peca entrarNaFila) {
        if (isEmpty()) {
            inicio = entrarNaFila;
            fim = entrarNaFila;

        } else {
            fim.proximo = entrarNaFila;
            fim = entrarNaFila;
        }
    }

    public void dequave() {
        if (!isEmpty()) {
            if (inicio == fim) {
                inicio = null;
                fim = null;
            } else {
                inicio = inicio.proximo;
            }
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
