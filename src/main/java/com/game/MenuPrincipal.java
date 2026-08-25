package com.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {
    @Override
    public void start(Stage menuPrincipal) {
        menuPrincipal.setTitle("ABIMABALL - Futebol de cabeças");
        menuPrincipal.setResizable(false);
        menuPrincipal.setScene(criarCena(menuPrincipal));
        menuPrincipal.show();
    }

    public static Scene criarCena(Stage menuPrincipal) {
        Label titulo = new Label("ABIMABALL");
        titulo.getStyleClass().add("titulo-jogo");

        Button btnJogar = new Button("JOGAR");
        btnJogar.getStyleClass().add("botao-menu");

        Button btnConfiguracoes = new Button("CONFIGURAÇÕES");
        btnConfiguracoes.getStyleClass().add("botao-menu");

        Button btnSair = new Button("SAIR");
        btnSair.getStyleClass().add("botao-menu");


        btnJogar.setOnAction(evento ->{
            System.out.println("Indo para seleção dos personagens...");
            Scene cenaSelecao = TelaSelecao.criarCena(menuPrincipal);
            menuPrincipal.setScene(cenaSelecao);
        });

        btnSair.setOnAction(evento ->{
            Platform.exit();
        });

        btnConfiguracoes.setOnAction(evento -> {
            Alert ajuda = new Alert(Alert.AlertType.INFORMATION);
            ajuda.initOwner(menuPrincipal);
            ajuda.setTitle("Como jogar");
            ajuda.setHeaderText("ABIMABALL - Controles");
            ajuda.setContentText("PLAYER 1\nA/D: mover   W: pular   C: chutar   S: poder\n\n"
                    + "PLAYER 2\nSetas: mover/pular   Espaço: chutar   Seta para baixo: poder\n\n"
                    + "A partida dura 90 segundos ou termina ao chegar a 5 gols. ESC pausa.");
            ajuda.showAndWait();
        });

        VBox telaMenu = new VBox(30); //gap
        telaMenu.setAlignment(Pos.CENTER); //CENtraliza pro meio da tela
        telaMenu.getChildren().addAll(titulo,btnJogar,btnConfiguracoes,btnSair); //anexa os componetnes criados
        telaMenu.getStyleClass().add("fundo-menu");

        Scene scene =  new Scene(telaMenu,1080,800);

        // verifica se o style.css está junto a pasta do Menu principal
        scene.getStylesheets().add(MenuPrincipal.class.getResource("style.css").toExternalForm());
        return scene;
    }
    public static void main(String[] args){
        launch(args);
    }
}
