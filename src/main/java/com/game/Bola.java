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

        // CORREÇÃO: O atrito multiplica a velocidade X para ir freando a bola aos poucos
        velocidadeX *= atrito_ar;

        x += velocidadeX;
        y += velocidadeY;
    }

    public void QuiqueChao(double alturaDoChao){
        // Colisão com o chão
        if (y + (raio * 2) >= alturaDoChao){
            y = alturaDoChao - (raio * 2);
            if (velocidadeY > 0.6) {
                velocidadeY = velocidadeY * quique;
            } else {
                velocidadeY = 0;
            }
        }

        // Colisão com as paredes laterais (evita a bola sumir da tela)
        if (x < 0) {
            x = 0;
            velocidadeX = -velocidadeX * 0.8; // Quica na parede esquerda
        }
        if (x + (raio * 2) > 1080) { // 1080 é a largura da sua tela
            x = 1080 - (raio * 2);
            velocidadeX = -velocidadeX * 0.8; // Quica na parede direita
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
}