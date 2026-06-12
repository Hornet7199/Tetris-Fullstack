package com.jogo.tetris.model;

public class FabricaPecas {

    public static Peca criarI() {
        return new Peca(new int[][] {
                { 1, 1, 1, 1 }
        });
    }

    public static Peca criarO() {
        return new Peca(new int[][] {
                { 2, 2 },
                { 2, 2 }
        });
    }

    public static Peca criarT() {
        return new Peca(new int[][] {
                { 0, 3, 0 },
                { 3, 3, 3 },
                { 0, 0, 0 }
        });
    }

    public static Peca criarS() {
        return new Peca(new int[][] {
                { 0, 4, 4 },
                { 4, 4, 0 },
                { 0, 0, 0 }
        });
    }

    public static Peca criarZ() {
        return new Peca(new int[][] {
                { 5, 5, 0 },
                { 0, 5, 5 },
                { 0, 0, 0 }
        });
    }

    public static Peca criarJ() {
        return new Peca(new int[][] {
                { 6, 0, 0 },
                { 6, 6, 6 },
                { 0, 0, 0 }
        });
    }

    public static Peca criarL() {
        return new Peca(new int[][] {
                { 0, 0, 7 },
                { 7, 7, 7 },
                { 0, 0, 0 }
        });
    }
}