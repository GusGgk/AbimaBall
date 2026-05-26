package com.game;

public class Enaldo extends PersonagemBase{

    public Enaldo() {
        super("Enaldo", 69, 5.0, "/com/game/sprites/enaldo.png");
    }

    @Override
    public void Poder() {
        System.out.println("Deixando adversário sonolento");
    }

    @Override
    public void desativarPoder() {

    }
}