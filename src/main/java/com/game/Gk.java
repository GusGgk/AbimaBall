package com.game;

public class Gk extends PersonagemBase{

    public Gk(String nome, int numeroCamisa, double velocidadeMovimento, String caminhoSprite) {
        super("Gk", 10, 5.0, "/com/game/sprites/gk.png");
    }

    @Override
    public void Poder() {
        System.out.println("Virando um macaco e pulando mais alto");
    }
}
