package com.game;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Bola {
    private double x;
    private double y;

    private double velocidadeX = 0;
    private double velocidadeY = 0;

    private final double gravidade = 0.25;
    private final double atrito_ar = 0.995;
    private final double quique = -0.60;
    private final double raio = 20;

    private ImageView sprite;
    private Image gifAnimado;
    private Image imagemParada;

    public Bola(double xInicial, double yInicial){
        this.x = xInicial;
        this.y = yInicial;

        gifAnimado = new Image(getClass().getResourceAsStream("/com/game/sprites/bolagirando.gif"));
        imagemParada = new Image(getClass().getResourceAsStream("/com/game/sprites/bola.png"));

        sprite = new ImageView(imagemParada);
        sprite.setFitWidth(raio * 2);
        sprite.setFitHeight(raio * 2);
        sprite.setPreserveRatio(true);
        sprite.setSmooth(false);

        atualizarPosicaoVisual();
    }

    public void aplicarFisica(){
        velocidadeY += gravidade;
        velocidadeY += atrito_ar;

        x += velocidadeX;
        y += velocidadeY;
    }

    public void QuiqueChao(double alturaDoChao){
        if (y + (raio * 2) >= alturaDoChao){
            //calcula exatamente em cima do chão para não afundar na terra
            y = alturaDoChao - (raio * 2);
            //quica se a colisão for acima de 0.6
            if (velocidadeY > 0.6) {
                velocidadeY = velocidadeY * quique;
            } else {
                velocidadeY = 0;
            }
        }
    }

    public void atualizarPosicaoVisual(){
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);

        //troca de gif e foto
        if (Math.abs(velocidadeX) > 0.5 || Math.abs(velocidadeY) > 1.5){
            if(sprite.getImage() != gifAnimado) sprite.setImage(gifAnimado);
        } else {
            if(sprite.getImage() != imagemParada) sprite.setImage(imagemParada);
        }
    }
    public ImageView getSprite() {
        return sprite;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getRaio() {
        return raio;
    }

}
