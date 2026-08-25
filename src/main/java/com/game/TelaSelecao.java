package com.game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaSelecao {

    // (Guarda o estado da seleção)
    private static String heroiP1 = null;
    private static String heroiP2 = null;
    private static Label titulo;

    public static Scene criarCena(Stage menuSelecao){
        heroiP1 = null;
        heroiP2 = null;

        VBox telaSelecao = new VBox(40);
        telaSelecao.setAlignment(Pos.CENTER);
        telaSelecao.getStyleClass().add("selecao-fundo");

        titulo = new Label("PLAYER 1: ESCOLHA SEU CRAQUE");
        titulo.getStyleClass().add("titulo-selecao");

        HBox containerJogadores = new HBox(20);
        containerJogadores.setAlignment(Pos.CENTER);

        containerJogadores.getChildren().addAll(
                criarCard("Gk", "/com/game/sprites/gk.png", menuSelecao),
                criarCard("Edu", "/com/game/sprites/edu.png", menuSelecao),
                criarCard("Gritten", "/com/game/sprites/gritten.png", menuSelecao),
                criarCard("DosanCodes", "/com/game/sprites/dosan.png", menuSelecao),
                criarCard("Enaldo", "/com/game/sprites/enaldo.png", menuSelecao)
        );

        Button voltar = new Button("VOLTAR");
        voltar.getStyleClass().add("botao-card");
        voltar.setOnAction(e -> menuSelecao.setScene(MenuPrincipal.criarCena(menuSelecao)));

        telaSelecao.getChildren().addAll(titulo, containerJogadores, voltar);
        Scene scene = new Scene(telaSelecao, 1080,800);
        scene.getStylesheets().add(TelaSelecao.class.getResource("style.css").toExternalForm());
        return scene;
    }

    private static VBox criarCard(String nome, String caminho, Stage menuSelecao) {
        VBox card = new VBox(15);
        card.getStyleClass().add("card-personagem");
        card.setAlignment(Pos.CENTER);

        Image img = new Image(TelaSelecao.class.getResourceAsStream(caminho));
        ImageView view = new ImageView(img);
        view.setFitHeight(150);
        view.setPreserveRatio(true);

        Label lblNome = new Label(nome);
        lblNome.getStyleClass().add("nome-personagem");

        Button btnSelecionar = new Button("SELECIONAR");
        btnSelecionar.getStyleClass().add("botao-card");

        btnSelecionar.setOnAction(evento -> {
            if (heroiP1 == null) {
                heroiP1 = nome;
                System.out.println("PLAYER 1 escolheu: " + heroiP1);
                titulo.setText("PLAYER 2: ESCOLHA SEU CRAQUE");
            } else if (heroiP2 == null) {
                heroiP2 = nome;
                System.out.println("PLAYER 2 escolheu: " + heroiP2);
                System.out.println("CONFRONTO DEFINIDO: " + heroiP1 + " VS " + heroiP2);

                // 3. Os dois escolheram! Criamos a cena do jogo passando as duas escolhas
                Scene cenaDoJogo = TelaJogo.criarCena(menuSelecao, heroiP1, heroiP2);
                menuSelecao.setScene(cenaDoJogo);
            }
        });

        card.getChildren().addAll(view, lblNome, btnSelecionar);
        return card;
    }
}
