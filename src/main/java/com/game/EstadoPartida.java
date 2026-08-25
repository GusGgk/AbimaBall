package com.game;

/** Mantém as regras de placar e duração separadas da interface JavaFX. */
public final class EstadoPartida {
    public static final int DURACAO_SEGUNDOS = 90;
    public static final int LIMITE_GOLS = 5;

    private int golsP1;
    private int golsP2;
    private double segundosRestantes = DURACAO_SEGUNDOS;

    public void atualizar(double segundos) {
        if (terminou()) {
            return;
        }
        segundosRestantes = Math.max(0, segundosRestantes - segundos);
    }

    public void registrarGol(int jogador) {
        if (terminou()) {
            return;
        }
        if (jogador == 1) {
            golsP1++;
        } else if (jogador == 2) {
            golsP2++;
        } else {
            throw new IllegalArgumentException("Jogador deve ser 1 ou 2");
        }
    }

    public boolean terminou() {
        return segundosRestantes <= 0 || golsP1 >= LIMITE_GOLS || golsP2 >= LIMITE_GOLS;
    }

    public int getVencedor() {
        if (!terminou() || golsP1 == golsP2) {
            return 0;
        }
        return golsP1 > golsP2 ? 1 : 2;
    }

    public int getGolsP1() {
        return golsP1;
    }

    public int getGolsP2() {
        return golsP2;
    }

    public int getSegundosRestantes() {
        return (int) Math.ceil(segundosRestantes);
    }

    public String getTempoFormatado() {
        int total = getSegundosRestantes();
        return String.format("%02d:%02d", total / 60, total % 60);
    }
}
