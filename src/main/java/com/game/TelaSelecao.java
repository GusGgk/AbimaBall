package com.game;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class TelaSelecao {

    public static Scene criarCena(Stage menuSelecao){
        VBox telaSelecao = new VBox(40);
        telaSelecao.setAlignment(Pos.CENTER);
        telaSelecao.getStyleClass().add("selecao-fundo");

        Label titulo = new Label("Escolha seu CRAQUE");
        titulo.getStyleClass().add("titulo-selecao");

        HBox containerJogadores = new HBox(20); //conteiner para 4 cards (jogadores)
        containerJogadores.setAlignment(Pos.CENTER);

        containerJogadores.getChildren().addAll(
                criarCard("Gk", "/com/game/sprites/gk.png", menuSelecao),
                criarCard("Edu", "/com/game/sprites/edu.png", menuSelecao),
                criarCard("Gritten", "/com/game/sprites/gritten.png", menuSelecao),
                criarCard("DosanCodes", "/com/game/sprites/dosan.png", menuSelecao),
                criarCard("Enaldo", "/com/game/sprites/enaldo.png", menuSelecao)
        );

        telaSelecao.getChildren().addAll(titulo, containerJogadores);
        Scene scene = new Scene(telaSelecao, 1000, 700);
        scene.getStylesheets().add(TelaSelecao.class.getResource("style.css").toExternalForm());
        return scene;
    }

    private static VBox criarCard(String nome, String caminho, Stage menuSelecao) {
        VBox card = new VBox(15);
        card.getStyleClass().add("card-personagem");
        card.setAlignment(Pos.CENTER);

        //sprite
        Image img = new Image(TelaSelecao.class.getResourceAsStream(caminho));
        ImageView view = new ImageView(img);
        view.setFitHeight(150);
        view.setPreserveRatio(true);

        Label lblNome = new Label(nome);
        Button btnSelecionar = new Button("SELECIONAR");

        btnSelecionar.setOnAction(evento -> {
            System.out.println("Selecionando: " + nome);
        });

        card.getChildren().addAll(view, lblNome, btnSelecionar);
        return card;
    }

}
