package com.game;

public class Dosan extends PersonagemBase {

    public Dosan(String nome, int numeroCamisa, double velocidadeMovimento, String caminhoSprite) {
        super("Dosan", 67, 5.0, "/com/game/sprites/dosan.png");
    }

    @Override
    public void Poder() {
        System.out.println("Soltando a fumaça da confusão");
    }
}
