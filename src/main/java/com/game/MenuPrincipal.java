package com.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {
    @Override
    public void start(Stage menuPrincipal) {
        Label titulo = new Label("ABIMABALL");
        titulo.getStyleClass().add("titulo-jogo");

        Button btnJogar = new Button("JOGAR");
        btnJogar.getStyleClass().add("btn-menu");

        Button btnConfiguracoes = new Button("CONFIGURAÇÕES");
        btnConfiguracoes.getStyleClass().add("btn-menu");

        Button btnSair = new Button("SAIR");
        btnSair.getStyleClass().add("btn-menu");


        btnJogar.setOnAction(evento ->{
            System.out.println("Indo para seleção dos personagens...");
            Scene cenaSelecao = TelaSelecao.criarCena(menuPrincipal);
            menuPrincipal.setScene(cenaSelecao);
        });

        btnSair.setOnAction(evento ->{
            Platform.exit();
        });

        VBox telaMenu = new VBox(30); //gap
        telaMenu.setAlignment(Pos.CENTER); //CENtraliza pro meio da tela
        telaMenu.getChildren().addAll(titulo,btnJogar,btnConfiguracoes,btnSair); //anexa os componetnes criados
        telaMenu.getStyleClass().add("fundo-menu");

        Scene scene =  new Scene(telaMenu,800,600);

        // verifica se o style.css está junto a pasta do Menu principal
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        menuPrincipal.setTitle("ABIMABALL - Futebol de cabeças");
        menuPrincipal.setScene(scene);
        menuPrincipal.setResizable(false); //tamanho da janela
        menuPrincipal.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
