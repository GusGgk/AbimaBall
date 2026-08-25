package com.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public final class TelaJogo {
    private TelaJogo() {
    }

    public static Scene criarCena(Stage palco, String p1Escolhido, String p2Escolhido) {
        Pane campo = new Pane();
        campo.getStyleClass().add("fundo-jogo");
        adicionarGols(campo);

        Bola bola = new Bola(520, 90);
        PersonagemBase jogador1 = carregarPersonagem(p1Escolhido);
        jogador1.setX(150);
        jogador1.setY(500);
        PersonagemBase jogador2 = carregarPersonagem(p2Escolhido);
        jogador2.setX(810);
        jogador2.setY(500);
        jogador2.getSprite().setScaleX(-1);

        campo.getChildren().addAll(jogador1.getSprite(), jogador2.getSprite(), bola.getSprite());

        Label placar = new Label();
        Label tempo = new Label();
        placar.getStyleClass().add("placar");
        tempo.getStyleClass().add("cronometro");
        HBox topo = new HBox(30, placar, tempo);
        topo.setAlignment(Pos.TOP_CENTER);
        topo.setPadding(new Insets(20));

        Label controles = new Label("P1  A/D mover  W pular  C chutar  S poder     |     P2  ←/→ mover  ↑ pular  ESPAÇO chutar  ↓ poder     |     ESC pausa");
        controles.getStyleClass().add("controles-jogo");
        BorderPane hud = new BorderPane();
        hud.setTop(topo);
        hud.setBottom(controles);
        BorderPane.setAlignment(controles, Pos.CENTER);
        BorderPane.setMargin(controles, new Insets(0, 0, 18, 0));
        hud.setMouseTransparent(true);

        VBox pausa = criarPainel("PAUSADO", "Pressione ESC para continuar");
        pausa.setVisible(false);

        VBox resultado = criarPainel("FIM DE JOGO", "");
        Label mensagemResultado = (Label) resultado.getChildren().get(1);
        Button revanche = new Button("REVANCHE");
        Button menu = new Button("MENU PRINCIPAL");
        revanche.getStyleClass().add("botao-menu");
        menu.getStyleClass().add("botao-menu");
        revanche.setOnAction(e -> palco.setScene(criarCena(palco, p1Escolhido, p2Escolhido)));
        menu.setOnAction(e -> palco.setScene(MenuPrincipal.criarCena(palco)));
        resultado.getChildren().addAll(revanche, menu);
        resultado.setVisible(false);

        StackPane raiz = new StackPane(campo, hud, pausa, resultado);
        Scene cena = new Scene(raiz, 1080, 800);
        cena.getStylesheets().add(TelaJogo.class.getResource("style.css").toExternalForm());
        GerenciadorTeclado.configurar(cena);

        MotorPartida[] referenciaMotor = new MotorPartida[1];
        Runnable atualizarHud = () -> {
            EstadoPartida estado = referenciaMotor[0].getEstado();
            placar.setText(p1Escolhido + "  " + estado.getGolsP1() + "  x  " + estado.getGolsP2() + "  " + p2Escolhido);
            tempo.setText(estado.getTempoFormatado());
        };
        Runnable terminar = () -> {
            EstadoPartida estado = referenciaMotor[0].getEstado();
            mensagemResultado.setText(estado.getVencedor() == 0
                    ? "EMPATE!"
                    : "PLAYER " + estado.getVencedor() + " VENCEU!");
            resultado.setVisible(true);
        };

        MotorPartida motor = new MotorPartida(bola, jogador1, jogador2, atualizarHud, terminar);
        referenciaMotor[0] = motor;
        atualizarHud.run();

        cena.setOnKeyPressed(evento -> {
            if (evento.getCode() == KeyCode.ESCAPE && !resultado.isVisible()) {
                motor.setPausado(!motor.isPausado());
                pausa.setVisible(motor.isPausado());
                evento.consume();
            }
        });
        palco.setOnCloseRequest(e -> motor.stop());
        Platform.runLater(raiz::requestFocus);
        motor.start();
        return cena;
    }

    private static void adicionarGols(Pane campo) {
        Image imagemGol = new Image(TelaJogo.class.getResourceAsStream("/com/game/sprites/gol.png"));
        ImageView esquerdo = criarGol(imagemGol, -190, 390, false);
        ImageView direito = criarGol(imagemGol, 1010, 390, true);
        campo.getChildren().addAll(esquerdo, direito);
    }

    private static ImageView criarGol(Image imagem, double x, double y, boolean espelhado) {
        ImageView gol = new ImageView(imagem);
        gol.setFitHeight(260);
        gol.setPreserveRatio(true);
        gol.setSmooth(false);
        gol.setTranslateX(x);
        gol.setTranslateY(y);
        if (espelhado) gol.setScaleX(-1);
        return gol;
    }

    private static VBox criarPainel(String titulo, String subtitulo) {
        Label labelTitulo = new Label(titulo);
        Label labelSubtitulo = new Label(subtitulo);
        labelTitulo.getStyleClass().add("titulo-overlay");
        labelSubtitulo.getStyleClass().add("subtitulo-overlay");
        VBox painel = new VBox(24, labelTitulo, labelSubtitulo);
        painel.setAlignment(Pos.CENTER);
        painel.setMaxSize(620, 440);
        painel.getStyleClass().add("painel-overlay");
        return painel;
    }

    private static PersonagemBase carregarPersonagem(String nome) {
        return switch (nome) {
            case "Edu" -> new Edu();
            case "DosanCodes" -> new Dosan();
            case "Enaldo" -> new Enaldo();
            case "Gritten" -> new Gritten();
            default -> new Gk();
        };
    }
}
