package com.game;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class MotorPartida extends AnimationTimer {
    private Bola bola;
    private PersonagemBase j1;
    private PersonagemBase j2;
    //chao
    private final double alturaChao = 620;
    private long ultimoQuadro = 0;


    //adicionar jogadores depois
    public MotorPartida(Bola bola, PersonagemBase j1, PersonagemBase j2){
        this.bola = bola;
        this.j1 = j1;
        this.j2 = j2;
    }

    @Override
    public void handle(long tempoAtual) {

        if (tempoAtual - ultimoQuadro < 16_666_666) {
            return;
        }

        ultimoQuadro = tempoAtual;

        // lendo teclado
        //p1
        boolean p1Esq = GerenciadorTeclado.isPressionada(KeyCode.A);
        boolean p1Dir = GerenciadorTeclado.isPressionada(KeyCode.D);
        boolean p1Pulo = GerenciadorTeclado.isPressionada(KeyCode.W);
        boolean p1Poder = GerenciadorTeclado.isPressionada(KeyCode.S);

        //p2
        boolean p2Esq = GerenciadorTeclado.isPressionada(KeyCode.LEFT);
        boolean p2Dir = GerenciadorTeclado.isPressionada(KeyCode.RIGHT);
        boolean p2Pulo = GerenciadorTeclado.isPressionada(KeyCode.UP);
        boolean p2Poder = GerenciadorTeclado.isPressionada(KeyCode.DOWN);

        if (p1Poder) j1.tentarAtivarPoder();
        if (p2Poder) j2.tentarAtivarPoder();
        j1.atualizarTimersPoder();
        j2.atualizarTimersPoder();

        //fisica
        j1.aplicarFisicaEControles(p1Esq,p1Dir,p1Pulo);
        j2.aplicarFisicaEControles(p2Esq,p2Dir,p2Pulo);
        bola.aplicarFisica();
        bola.QuiqueChao(alturaChao);

        j1.atualizarPosicaoVisual();
        j2.atualizarPosicaoVisual();
        bola.atualizarPosicaoVisual();

    }
}
