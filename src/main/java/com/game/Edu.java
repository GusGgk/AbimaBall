package com.game;

import javafx.scene.image.Image;

public class Edu extends PersonagemBase{
    private Image edumovel;
    public Edu() {
        super("Edu", 7, 5.0, "/com/game/sprites/edu.png");
        edumovel = new Image(getClass().getResourceAsStream("/com/game/sprites/edumovel.png"));
    }
    @Override
    public void Poder() {
        System.out.println("Transformação: Carona do Edu - EduMovel!");
        this.velocidadeMovimentoAtual = 11.0;
        this.sprite.setImage(edumovel);
    }

    @Override
    public void desativarPoder() {
        System.out.println("O modo turbo acabou - Edu desativando poder");
        this.velocidadeMovimentoAtual = this.velocidadeMovimentoBase;
        this.sprite.setImage(this.imagemOriginal);

    }
}
