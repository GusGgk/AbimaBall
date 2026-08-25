package com.game;

public class Enaldo extends PersonagemBase{

    public Enaldo() {
        super("Enaldo", 69, 5.0, "/com/game/sprites/enaldo.png");
    }

    @Override
    public void Poder() {
        System.out.println("Modo lenda: mais velocidade e impulsão!");
        this.velocidadeMovimentoAtual = 7.0;
        this.forcaPuloAtual = -12.0;
    }

    @Override
    public void desativarPoder() {
        this.velocidadeMovimentoAtual = this.velocidadeMovimentoBase;
        this.forcaPuloAtual = this.forcaPulo;
    }
}
