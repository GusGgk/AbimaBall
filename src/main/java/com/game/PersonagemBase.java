package com.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class PersonagemBase {
    private String nome;
    private int numeroCamisa;

    private double x;
    private double y;
    private double velocidadeY;
    private double velocidadeMovimento;

    // raiz visual
    protected ImageView sprite;


    public PersonagemBase(String nome, int numeroCamisa,double velocidadeMovimento, String caminhoSprite){
        this.nome = nome;
        this.numeroCamisa = numeroCamisa;
        this.velocidadeMovimento = velocidadeMovimento;
        Image img = new Image(getClass().getResourceAsStream(caminhoSprite));
        this.sprite = new ImageView(img);

        // pixels configurados
        this.sprite.setSmooth(false);
        this.sprite.setFitWidth(128);
        this.sprite.setPreserveRatio(true);
    }


    public void andarFrente(){
        this.x += velocidadeMovimento;
        atualizarPosicaoVisual();
    }

    public void andarTras(){
        this.x -= velocidadeMovimento;
        atualizarPosicaoVisual();
    }

    public void chutar(){

    }

    public void pular(){
        this.velocidadeY = -15;
    }

    public abstract void Poder();

    public void atualizarPosicaoVisual(){
        if (sprite !=null){
            sprite.setTranslateX(this.x);
            sprite.setTranslateY(this.y);
        }
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getNumeroCamisa() {
        return numeroCamisa;
    }


    public ImageView getSprite(){
        return sprite;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
        atualizarPosicaoVisual();
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
        atualizarPosicaoVisual();
    }
}