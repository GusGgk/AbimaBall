package com.game;

public class Edu extends PersonagemBase{
    public Edu(String nome, int numeroCamisa, double velocidadeMovimento, String caminhoSprite) {
        super(nome, numeroCamisa, velocidadeMovimento, caminhoSprite);
    }
    @Override
    public void Poder() {
        System.out.println("Virando um carro");
    }
}
