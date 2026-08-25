package com.game;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Bola {
    private double x;
    private double y;

    private double velocidadeX = 0;
    private double velocidadeY = 0;

    private static final double GRAVIDADE = 0.25;
    private static final double ATRITO_AR = 0.995;
    private static final double QUIQUE = -0.60;
    private static final double LARGURA_CAMPO = 1080;
    private final double raio = 20;

    private ImageView sprite;
    private Image gifAnimado;
    private Image imagemParada;

    public Bola(double xInicial, double yInicial){
        this.x = xInicial;
        this.y = yInicial;

        gifAnimado = new Image(Bola.class.getResourceAsStream("/com/game/sprites/bolagirando.gif"));
        imagemParada = new Image(Bola.class.getResourceAsStream("/com/game/sprites/bola.png"));

        sprite = new ImageView(imagemParada);
        sprite.setFitWidth(raio * 2);
        sprite.setFitHeight(raio * 2);
        sprite.setPreserveRatio(true);
        sprite.setSmooth(false);

        atualizarPosicaoVisual();
    }

    public void aplicarFisica(){
        velocidadeY += GRAVIDADE;
        velocidadeX *= ATRITO_AR;
        x += velocidadeX;
        y += velocidadeY;
    }

    public void QuiqueChao(double alturaDoChao){
        // Colisão com o chão
        if (y + (raio * 2) >= alturaDoChao){
            y = alturaDoChao - (raio * 2);
            if (velocidadeY > 0.6) {
                velocidadeY = velocidadeY * QUIQUE;
            } else {
                velocidadeY = 0;
            }
        }

    }

    public void quicarParedes() {
        if (x < 0) {
            x = 0;
            velocidadeX = Math.abs(velocidadeX) * 0.8;
        } else if (x + getDiametro() > LARGURA_CAMPO) {
            x = LARGURA_CAMPO - getDiametro();
            velocidadeX = -Math.abs(velocidadeX) * 0.8;
        }
    }

    public void atualizarPosicaoVisual(){
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);

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

    public double getDiametro() {
        return raio * 2;
    }

    // CORREÇÃO: Agora os métodos realmente alteram os valores da bola!
    public void setVelocidadeX(double v) {
        this.velocidadeX = v;
    }

    public void setVelocidadeY(double v) {
        this.velocidadeY = v;
    }

    public void setX(double v) {
        this.x = v;
    }

    public void setY(double v) {
        this.y = v;
    }

    public double getVelocidadeY() {
        return velocidadeY;
    }

    public double getVelocidadeX(){
        return velocidadeX;
    }
}
