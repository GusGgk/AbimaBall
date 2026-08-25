package com.game;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GerenciadorTeclado {

    private static final Set<KeyCode> teclasPressionadas = new HashSet<>();

    public static void configurar(Scene cena) {
        teclasPressionadas.clear();
        // pressionar
        cena.addEventFilter(KeyEvent.KEY_PRESSED, evento -> teclasPressionadas.add(evento.getCode()));
        //soltar
        cena.addEventFilter(KeyEvent.KEY_RELEASED, evento -> teclasPressionadas.remove(evento.getCode()));
        cena.windowProperty().addListener((obs, anterior, atual) -> {
            if (atual != null) {
                atual.focusedProperty().addListener((o, tinhaFoco, temFoco) -> {
                    if (!temFoco) teclasPressionadas.clear();
                });
            }
        });
    }

    public static boolean isPressionada(KeyCode tecla) {
        return teclasPressionadas.contains(tecla);
    }

    public static void limpar() {
        teclasPressionadas.clear();
    }
}
