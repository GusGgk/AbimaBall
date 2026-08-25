package com.game;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

/** Loop principal da partida, com física fixa a 60 atualizações por segundo. */
public class MotorPartida extends AnimationTimer {
    private static final double PASSO_FIXO = 1.0 / 60.0;
    private static final double ALTURA_CHAO = 620;
    private static final double LINHA_GOL_ESQUERDO = 70;
    private static final double LINHA_GOL_DIREITO = 1010;
    private static final double ALTURA_TRAVESSAO = 390;

    private final Bola bola;
    private final PersonagemBase j1;
    private final PersonagemBase j2;
    private final EstadoPartida estado = new EstadoPartida();
    private final Runnable aoAtualizarHud;
    private final Runnable aoTerminar;

    private long ultimoQuadro;
    private double acumulador;
    private boolean pausado;
    private boolean terminou;
    private boolean p1PuloAnterior;
    private boolean p2PuloAnterior;
    private boolean p1PoderAnterior;
    private boolean p2PoderAnterior;

    public MotorPartida(Bola bola, PersonagemBase j1, PersonagemBase j2,
                        Runnable aoAtualizarHud, Runnable aoTerminar) {
        this.bola = bola;
        this.j1 = j1;
        this.j2 = j2;
        this.aoAtualizarHud = aoAtualizarHud;
        this.aoTerminar = aoTerminar;
    }

    @Override
    public void handle(long agora) {
        if (ultimoQuadro == 0) {
            ultimoQuadro = agora;
            return;
        }

        double decorrido = Math.min((agora - ultimoQuadro) / 1_000_000_000.0, 0.25);
        ultimoQuadro = agora;
        if (pausado || terminou) return;

        estado.atualizar(decorrido);
        acumulador += decorrido;
        while (acumulador >= PASSO_FIXO) {
            atualizarFisica();
            acumulador -= PASSO_FIXO;
        }

        j1.atualizarPosicaoVisual();
        j2.atualizarPosicaoVisual();
        bola.atualizarPosicaoVisual();
        aoAtualizarHud.run();

        if (estado.terminou()) {
            terminou = true;
            stop();
            GerenciadorTeclado.limpar();
            aoTerminar.run();
        }
    }

    private void atualizarFisica() {
        boolean p1Esq = GerenciadorTeclado.isPressionada(KeyCode.A);
        boolean p1Dir = GerenciadorTeclado.isPressionada(KeyCode.D);
        boolean p1Pulo = GerenciadorTeclado.isPressionada(KeyCode.W);
        boolean p1Poder = GerenciadorTeclado.isPressionada(KeyCode.S);
        boolean p1Chute = GerenciadorTeclado.isPressionada(KeyCode.C);

        boolean p2Esq = GerenciadorTeclado.isPressionada(KeyCode.LEFT);
        boolean p2Dir = GerenciadorTeclado.isPressionada(KeyCode.RIGHT);
        boolean p2Pulo = GerenciadorTeclado.isPressionada(KeyCode.UP);
        boolean p2Poder = GerenciadorTeclado.isPressionada(KeyCode.DOWN);
        boolean p2Chute = GerenciadorTeclado.isPressionada(KeyCode.SPACE);

        if (p1Poder && !p1PoderAnterior) j1.tentarAtivarPoder();
        if (p2Poder && !p2PoderAnterior) j2.tentarAtivarPoder();
        j1.atualizarTimersPoder();
        j2.atualizarTimersPoder();

        j1.aplicarFisicaEControles(p1Esq, p1Dir, p1Pulo && !p1PuloAnterior);
        j2.aplicarFisicaEControles(p2Esq, p2Dir, p2Pulo && !p2PuloAnterior);
        resolverColisaoJogadores();

        bola.aplicarFisica();
        bola.QuiqueChao(ALTURA_CHAO);
        if (bola.getY() + bola.getDiametro() < ALTURA_TRAVESSAO) bola.quicarParedes();

        checarColisaoJogadorBola(j1, p1Chute, 1);
        checarColisaoJogadorBola(j2, p2Chute, 2);
        checarTravesEGols();

        p1PuloAnterior = p1Pulo;
        p2PuloAnterior = p2Pulo;
        p1PoderAnterior = p1Poder;
        p2PoderAnterior = p2Poder;
    }

    private void checarTravesEGols() {
        double centroX = bola.getX() + bola.getRaio();
        double base = bola.getY() + bola.getDiametro();

        if (base >= ALTURA_TRAVESSAO && bola.getY() < ALTURA_TRAVESSAO
                && (centroX <= LINHA_GOL_ESQUERDO || centroX >= LINHA_GOL_DIREITO)) {
            bola.setY(ALTURA_TRAVESSAO - bola.getDiametro());
            bola.setVelocidadeY(-Math.abs(bola.getVelocidadeY()) * 0.7);
            return;
        }
        if (base > ALTURA_TRAVESSAO + 4 && centroX < LINHA_GOL_ESQUERDO) {
            marcarGol(2);
        } else if (base > ALTURA_TRAVESSAO + 4 && centroX > LINHA_GOL_DIREITO) {
            marcarGol(1);
        }
    }

    private void marcarGol(int jogador) {
        estado.registrarGol(jogador);
        reiniciarPosicoes();
        aoAtualizarHud.run();
    }

    private void reiniciarPosicoes() {
        bola.setX(520);
        bola.setY(90);
        bola.setVelocidadeX(0);
        bola.setVelocidadeY(0);

        j1.reiniciarEstado();
        j1.setX(150);
        j1.setY(500);
        j2.reiniciarEstado();
        j2.setX(810);
        j2.setY(500);
    }

    private void checarColisaoJogadorBola(PersonagemBase jogador, boolean chutando, int numeroJogador) {
        double largura = Math.max(55, jogador.getLargura() * 0.62);
        double esquerda = jogador.getX() + (jogador.getLargura() - largura) / 2;
        double topo = jogador.getY() + 5;
        double direita = esquerda + largura;
        double base = jogador.getY() + 115;
        double centroBolaX = bola.getX() + bola.getRaio();
        double centroBolaY = bola.getY() + bola.getRaio();
        double pontoX = limitar(centroBolaX, esquerda, direita);
        double pontoY = limitar(centroBolaY, topo, base);
        double dx = centroBolaX - pontoX;
        double dy = centroBolaY - pontoY;

        if (dx * dx + dy * dy > bola.getRaio() * bola.getRaio()) return;

        double direcao = numeroJogador == 1 ? 1 : -1;
        if (chutando) {
            bola.setVelocidadeX(14.5 * direcao);
            bola.setVelocidadeY(-9.5);
        } else {
            double afastamento = dx == 0 ? direcao : Math.signum(dx);
            bola.setVelocidadeX(4.2 * afastamento + jogador.velocidadeX * 0.45);
            bola.setVelocidadeY(Math.min(bola.getVelocidadeY(), -1.8));
        }

        if (Math.abs(dx) >= Math.abs(dy)) {
            bola.setX(dx >= 0 ? direita : esquerda - bola.getDiametro());
        } else if (dy < 0) {
            bola.setY(topo - bola.getDiametro());
        }
    }

    private void resolverColisaoJogadores() {
        double largura = 72;
        boolean mesmaAltura = j1.getY() < j2.getY() + 115 && j1.getY() + 115 > j2.getY();
        if (!mesmaAltura || j1.getX() + largura <= j2.getX() || j2.getX() + largura <= j1.getX()) return;

        double meio = (j1.getX() + j2.getX() + largura) / 2;
        j1.setX(Math.max(0, meio - largura));
        j2.setX(Math.min(950, meio));
    }

    private double limitar(double valor, double minimo, double maximo) {
        return Math.max(minimo, Math.min(maximo, valor));
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public void setPausado(boolean pausado) {
        this.pausado = pausado;
        GerenciadorTeclado.limpar();
    }

    public boolean isPausado() {
        return pausado;
    }
}
