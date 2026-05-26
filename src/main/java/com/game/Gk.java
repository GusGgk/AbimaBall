package com.game;
import javafx.scene.image.Image;

public class Gk extends PersonagemBase{
    private Image gkMonkey;

    public Gk() {
        super("Gk", 10, 5.0, "/com/game/sprites/gk.png");
        gkMonkey = new Image(getClass().getResourceAsStream("/com/game/sprites/gkmonkey.png"));
    }

    @Override
    public void Poder() {
        System.out.println("Transformação: Virando o GkMonkey");
        this.forcaPuloAtual = - 15;
        this.sprite.setImage(gkMonkey);
    }

    @Override
    public void desativarPoder() {
        System.out.println("O poder de Gk acabou...Destransformando...");
        this.forcaPuloAtual = this.forcaPulo;
        this.sprite.setImage(this.imagemOriginal);

    }
}
