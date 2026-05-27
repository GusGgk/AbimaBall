package com.game;

public class Dosan extends PersonagemBase {

    public Dosan() {
        super("Dosan", 67, 5.0, "/com/game/sprites/dosan.png");
    }

    @Override
    public void Poder() {
        System.out.println("Soltando a fumaça da confusão");
    }

    @Override
    public void desativarPoder() {

    }
}
