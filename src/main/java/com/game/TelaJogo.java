package com.game;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TelaJogo {

    public static Scene criarCena(String p1Escolhido, String p2Escolhido) {
        Pane campoJogo = new Pane();
        campoJogo.getStyleClass().add("fundo-jogo");

        Image imgGol = new Image(TelaJogo.class.getResourceAsStream("/com/game/sprites/gol.png"));

        double chaoVisual = 500;
        double personagemAltura = 120;

        // Gol Esquerdo
        ImageView spriteGolEsquerdo = new ImageView(imgGol);
        spriteGolEsquerdo.setFitHeight(260);
        spriteGolEsquerdo.setPreserveRatio(true);
        spriteGolEsquerdo.setSmooth(false);
        spriteGolEsquerdo.setTranslateX(-150);
        spriteGolEsquerdo.setTranslateY(390);

        // Gol Direito
        ImageView spriteGolDireito = new ImageView(imgGol);
        spriteGolDireito.setFitHeight(260);
        spriteGolDireito.setPreserveRatio(true);
        spriteGolDireito.setSmooth(false);
        spriteGolDireito.setTranslateX(950);
        spriteGolDireito.setTranslateY(390);
        spriteGolDireito.setScaleX(-1);

        Bola bolaPrincipal = new Bola(500, 50);

        // Player 1 (Esquerda)
        PersonagemBase jogador1 = carregarPersonagem(p1Escolhido);
        jogador1.setX(100);
        jogador1.setY(500);

        // Player 2 (Direita)
        PersonagemBase jogador2 = carregarPersonagem(p2Escolhido);
        jogador2.setX(800);
        jogador2.setY(500);
        jogador2.getSprite().setScaleX(-1); //espelho

        campoJogo.getChildren().addAll(
                spriteGolEsquerdo,
                spriteGolDireito,
                jogador1.getSprite(),
                jogador2.getSprite(),
                bolaPrincipal.getSprite()
        );

        Scene scene = new Scene(campoJogo, 1080, 800);
        scene.getStylesheets().add(TelaJogo.class.getResource("style.css").toExternalForm());

        GerenciadorTeclado.configurar(scene);

        //Forca pedido de uso de teclado
        Platform.runLater(() -> campoJogo.requestFocus());

        MotorPartida motor = new MotorPartida(bolaPrincipal, jogador1, jogador2);
        motor.start();

        return scene; // encerra a criação da tela
    }

    private static PersonagemBase carregarPersonagem(String nome) {
        switch (nome) {
            case "Gk": return new Gk();
            case "Edu": return new Edu();
            case "DosanCodes": return new Dosan();
            case "Enaldo": return new Enaldo();
            case "Gritten":
                return new Gritten();
            default:
                return new Gk();
        }
    }
}