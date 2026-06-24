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

    // Adicionamos o 'isPlayer1' para sabermos para onde o boneco está olhando
    private void checarColisaoJogadorBola(PersonagemBase jogador, boolean tentandoChutar, boolean isPlayer1) {

        double corpoLargura = 40;
        double corpoAltura = 110;
        double jogX = jogador.getX() + 40;
        double jogY = jogador.getY() + 10;

        // 1. HITBOX DA BOLA MENOR E CENTRALIZADA
        // Diminuímos a caixa de impacto de 40 para 30 e empurramos 5px para o meio
        double bolaTamanho = 30;
        double bolaX = bola.getX() + 5;
        double bolaY = bola.getY() + 5;

        // Passar limpo por cima da bola
        if ((jogador.getY() + corpoAltura) < (bolaY + (bolaTamanho / 2))) {
            return;
        }

        boolean colidiuX = (bolaX + bolaTamanho > jogX) && (bolaX < jogX + corpoLargura);
        boolean colidiuY = (bolaY + bolaTamanho > jogY) && (bolaY < jogY + corpoAltura);

        if (colidiuX && colidiuY) {

            double centroJogador = jogX + (corpoLargura / 2);
            double centroBola = bolaX + (bolaTamanho / 2);

            // --- 2. A MÁGICA DO FANTASMA NAS COSTAS ---
            // Se for o Player 1 e a bola estiver na ESQUERDA dele (costas), ignora!
            if (isPlayer1 && centroBola < centroJogador) {
                return;
            }
            // Se for o Player 2 e a bola estiver na DIREITA dele (costas), ignora!
            if (!isPlayer1 && centroBola > centroJogador) {
                return;
            }

            // --- 3. CONDUÇÃO VS CHUTE (Sempre pra frente) ---
            if (tentandoChutar) {
                // CHUTE FORTE
                if (isPlayer1) {
                    bola.setVelocidadeX(16.0);
                    bola.setX(jogX + corpoLargura + 2);
                } else {
                    bola.setVelocidadeX(-16.0);
                    bola.setX(jogX - bolaTamanho - 2);
                }
                bola.setVelocidadeY(-10.0); // Chute forte pro alto
                bola.setY(bola.getY() - 5);

            } else {
                // CONDUÇÃO SUAVE, LENTA E RASTEIRA
                if (isPlayer1) {
                    bola.setVelocidadeX(2.5); // Velocidade bem reduzida
                    bola.setX(jogX + corpoLargura + 1);
                } else {
                    bola.setVelocidadeX(-2.5); // Velocidade bem reduzida
                    bola.setX(jogX - bolaTamanho - 1);
                }
                // Garante que a bola não fique quicando igual pipoca
                bola.setVelocidadeY(0);
            }
        }
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
        boolean p1Chute = GerenciadorTeclado.isPressionada(KeyCode.C);

        //p2
        boolean p2Esq = GerenciadorTeclado.isPressionada(KeyCode.LEFT);
        boolean p2Dir = GerenciadorTeclado.isPressionada(KeyCode.RIGHT);
        boolean p2Pulo = GerenciadorTeclado.isPressionada(KeyCode.UP);
        boolean p2Poder = GerenciadorTeclado.isPressionada(KeyCode.DOWN);
        boolean p2Chute = GerenciadorTeclado.isPressionada(KeyCode.SPACE);

        if (p1Poder) j1.tentarAtivarPoder();
        if (p2Poder) j2.tentarAtivarPoder();
        j1.atualizarTimersPoder();
        j2.atualizarTimersPoder();

        //fisica
        j1.aplicarFisicaEControles(p1Esq,p1Dir,p1Pulo);
        j2.aplicarFisicaEControles(p2Esq,p2Dir,p2Pulo);
        bola.aplicarFisica();
        bola.QuiqueChao(alturaChao);

        checarColisaoJogadorBola(j1, p1Chute, true);
        checarColisaoJogadorBola(j2, p2Chute, false);


        j1.atualizarPosicaoVisual();
        j2.atualizarPosicaoVisual();
        bola.atualizarPosicaoVisual();

    }
}
