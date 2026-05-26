package com.game;

import javafx.scene.image.Image;

public class Gritten extends PersonagemBase{
    private Image tutorCabecao;

    public Gritten() {
        super("Gritten", 5, 5, "/com/game/sprites/gritten.png");
        tutorCabecao = new Image(getClass().getResourceAsStream("/com/game/sprites/grittenCabecao.png"));

    }

    @Override
    public void Poder() {
        System.out.println("Transformação: Ativando o Modo Tutor Cabeção");
        this.sprite.setImage(this.tutorCabecao);
        this.margemPe = 25;
        double multiplicador = 1.5;
        this.alturaPersonagemAtual = this.alturaPersonagem * multiplicador;

        this.y -= (this.alturaPersonagemAtual - this.alturaPersonagem);
        this.sprite.setFitHeight(this.alturaPersonagemAtual);

    }

    @Override
    public void desativarPoder() {
        System.out.println("Gritten voltou ao tamanho normal.");
        this.sprite.setImage(this.imagemOriginal);

        this.y += (this.alturaPersonagemAtual - this.alturaPersonagem);

        // Restaura a altura física
        this.alturaPersonagemAtual = this.alturaPersonagem;
        this.margemPe = 0;

        // Restaura o visual
        this.sprite.setFitHeight(this.alturaPersonagemAtual);

    }
}
