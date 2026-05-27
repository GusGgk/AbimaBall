package com.game;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GerenciadorTeclado {

    private static Set<KeyCode> teclasPressionadas = new HashSet<>();

    public static void configurar(Scene cena) {
        // pressionar
        cena.addEventFilter(KeyEvent.KEY_PRESSED, evento -> teclasPressionadas.add(evento.getCode()));
        //soltar
        cena.addEventFilter(KeyEvent.KEY_RELEASED, evento -> teclasPressionadas.remove(evento.getCode()));
    }

    public static boolean isPressionada(KeyCode tecla) {
        return teclasPressionadas.contains(tecla);
    }
}