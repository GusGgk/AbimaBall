package com.game;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TelaJogo {

    public static Scene criarCena(String p1Escolhido, String p2Escolhido) {
        Pane campoJogo = new Pane();
        campoJogo.getStyleClass().add("fundo-jogo");

        Image imgGol = new Image(TelaJogo.class.getResourceAsStream("/com/game/sprites/gol.png"));

        // Gol Esquerdo
        ImageView spriteGolEsquerdo = new ImageView(imgGol);
        spriteGolEsquerdo.setFitHeight(260); // Tamanho gol
        spriteGolEsquerdo.setPreserveRatio(true);
        spriteGolEsquerdo.setSmooth(false);
        spriteGolEsquerdo.setTranslateX(-150);
        spriteGolEsquerdo.setTranslateY(390); //gol toque a grama

        // Gol Direito
        ImageView spriteGolDireito = new ImageView(imgGol);
        spriteGolDireito.setFitHeight(260);
        spriteGolDireito.setPreserveRatio(true);
        spriteGolDireito.setSmooth(false);
        spriteGolDireito.setTranslateX(950);
        spriteGolDireito.setTranslateY(390);
        //espelhar
        spriteGolDireito.setScaleX(-1);

        Bola bolaPrincipal = new Bola(540, 50);

        // Player 1 (Esquerda)
        ImageView spriteP1 = carregarSpriteDoPersonagem(p1Escolhido);
        spriteP1.setTranslateX(100);
        spriteP1.setTranslateY(500);

        //Player 2 (Direita)
        ImageView spriteP2 = carregarSpriteDoPersonagem(p2Escolhido);
        spriteP2.setTranslateX(800);
        spriteP2.setTranslateY(500);
        spriteP2.setScaleX(-1); // Espelhar P2


        // --- ADICIONANDO TUDO NO CAMPO ---
        // A ordem  importa. Coisas adicionadas depois ficam na frente visualmente (Z-Order).
        campoJogo.getChildren().addAll(spriteGolEsquerdo, spriteGolDireito, spriteP1, spriteP2, bolaPrincipal.getSprite());

        Scene scene = new Scene(campoJogo, 1080,800);
        scene.getStylesheets().add(TelaJogo.class.getResource("style.css").toExternalForm());
        return scene;
    }

    private static ImageView carregarSpriteDoPersonagem(String nome) {
        String caminho = "";
        switch (nome) {
            case "Gk": caminho = "/com/game/sprites/gk.png"; break;
            case "Edu": caminho = "/com/game/sprites/edu.png"; break;
            case "Gritten": caminho = "/com/game/sprites/gritten.png"; break;
            case "DosanCodes": caminho = "/com/game/sprites/dosan.png"; break;
            case "Enaldo": caminho = "/com/game/sprites/enaldo.png"; break;
            default: caminho = "/com/game/sprites/gk.png"; break;
        }

        Image img = new Image(TelaJogo.class.getResourceAsStream(caminho));
        ImageView view = new ImageView(img);
        view.setFitHeight(120); // Tamanho do personagem
        view.setPreserveRatio(true);
        view.setSmooth(false);

        return view;
    }
}