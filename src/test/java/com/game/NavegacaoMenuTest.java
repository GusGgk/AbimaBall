package com.game;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledOnOs(OS.WINDOWS)
class NavegacaoMenuTest {
    @BeforeAll
    static void iniciarJavaFx() throws Exception {
        CountDownLatch pronto = new CountDownLatch(1);
        Platform.startup(pronto::countDown);
        assertTrue(pronto.await(5, TimeUnit.SECONDS));
    }

    @AfterAll
    static void encerrarJavaFx() {
        Platform.exit();
    }

    @Test
    void botaoJogarAbreTelaDeSelecao() throws Exception {
        AtomicReference<Throwable> erro = new AtomicReference<>();
        CountDownLatch terminou = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage palco = new Stage();
                Scene menu = MenuPrincipal.criarCena(palco);
                palco.setScene(menu);

                Button jogar = encontrarBotao(menu.getRoot(), "JOGAR");
                jogar.fire();

                assertNotSame(menu, palco.getScene());
                assertTrue(palco.getScene().getRoot().getStyleClass().contains("selecao-fundo"));
                palco.close();
            } catch (Throwable t) {
                erro.set(t);
            } finally {
                terminou.countDown();
            }
        });

        assertTrue(terminou.await(10, TimeUnit.SECONDS));
        if (erro.get() != null) throw new AssertionError(erro.get());
    }

    private Button encontrarBotao(Parent raiz, String texto) {
        for (Node node : raiz.getChildrenUnmodifiable()) {
            if (node instanceof Button botao && texto.equals(botao.getText())) return botao;
            if (node instanceof Parent grupo) {
                try {
                    return encontrarBotao(grupo, texto);
                } catch (IllegalStateException ignorado) {
                    // Continua procurando nos outros ramos.
                }
            }
        }
        throw new IllegalStateException("Botão não encontrado: " + texto);
    }
}
