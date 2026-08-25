package com.game;

public class Dosan extends PersonagemBase {

    public Dosan() {
        super("Dosan", 67, 5.0, "/com/game/sprites/dosan.png");
    }

    @Override
    public void Poder() {
        System.out.println("Fumaça ninja: velocidade aumentada!");
        this.velocidadeMovimentoAtual = 8.0;
        this.forcaPuloAtual = -10.0;
    }

    @Override
    public void desativarPoder() {
        this.velocidadeMovimentoAtual = this.velocidadeMovimentoBase;
        this.forcaPuloAtual = this.forcaPulo;
    }
}
