package com.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class PersonagemBase {
    private String nome;
    private int numeroCamisa;

    protected double margemPe = 0;

    protected double x;
    protected double y;
    protected double velocidadeY = 0;
    protected double velocidadeX = 0;

    protected double velocidadeMovimento;
    protected double velocidadeMovimentoBase;
    protected double velocidadeMovimentoAtual;

    protected  final double forcaPulo = -8.0;
    protected double forcaPuloAtual;


    protected final double alturaPersonagem = 120;
    protected double alturaPersonagemAtual;

    protected final double gravidade = 0.6;
    protected final double alturaChao = 620;
    // raiz visual
    protected ImageView sprite;
    protected Image imagemOriginal;

    //poderes
    protected boolean poderAtivo = false;
    protected long  tempoFimPoder = 0;
    protected long  tempoProximoUso = 0;


    public PersonagemBase(String nome, int numeroCamisa, double velocidadeMovimento, String caminhoSprite) {
        this.nome = nome;
        this.numeroCamisa = numeroCamisa;
        this.velocidadeMovimento = velocidadeMovimento;

        this.imagemOriginal = new Image(getClass().getResourceAsStream(caminhoSprite));
        this.sprite = new ImageView(imagemOriginal);

        // Pixels configurados
        this.sprite.setSmooth(false);
        this.sprite.setFitHeight(120);
        this.sprite.setPreserveRatio(true);

        this.velocidadeMovimentoBase = this.velocidadeMovimento;
        this.velocidadeMovimentoAtual = this.velocidadeMovimento;
        this.alturaPersonagemAtual = this.alturaPersonagem;
        this.forcaPuloAtual = this.forcaPulo;

        atualizarPosicaoVisual();
    }

    //controle motor
    public void aplicarFisicaEControles(boolean indoEsquerda, boolean indoDireita, boolean pulando){
        if (indoEsquerda){
            velocidadeX = -velocidadeMovimentoAtual;
        } else if (indoDireita){
            velocidadeX = velocidadeMovimentoAtual;
        } else {
            velocidadeX = 0; //parar
        }
        //pulo
        if (pulando && (y + alturaPersonagemAtual - margemPe) >= alturaChao){
            pular();
        }

        //gravidade e mov
        velocidadeY += gravidade;
        x += velocidadeX;
        y += velocidadeY;

        //limites
        if ((y + alturaPersonagemAtual - margemPe) >= alturaChao){
            y = alturaChao - alturaPersonagemAtual + margemPe;
            velocidadeY = 0;
        }
        if (x<0) x = 0;
        if (x > 950) x = 950;
    }

    public void tentarAtivarPoder(){
        long tempoAtual = System.currentTimeMillis();

        if(!poderAtivo && tempoAtual >= tempoProximoUso){
            poderAtivo = true;
            tempoFimPoder = tempoAtual + 5000;
            tempoProximoUso = tempoAtual + 20000;
            Poder();
        }
    }

    // Metodo para atualizar o tempo do poder
    public void atualizarTimersPoder() {
        if (poderAtivo && System.currentTimeMillis() >= tempoFimPoder) {
            poderAtivo = false;
            desativarPoder(); // Desliga as modificações
        }
    }

    public void chutar(){
        System.out.println(getNome() + " deu um chute no gol!");
    }

    public void pular(){
        this.velocidadeY = forcaPuloAtual;
    }

    public abstract void Poder();
    public abstract void desativarPoder(); // Cada personagem precisa saber como voltar ao normal

    public void atualizarPosicaoVisual(){
        if (sprite !=null){
            sprite.setTranslateX(this.x);
            sprite.setTranslateY(this.y);
        }
    }
    public String getNome() {
        return nome;
    }

    public ImageView getSprite(){
        return sprite;
    }


    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVelocidadeY(double velocidadeY) {
        this.velocidadeY = velocidadeY;
    }

    public void setVelocidadeX(double velocidadeX) {
        this.velocidadeX = velocidadeX;
    }



    public void setScaleX(int i) {
    }
}