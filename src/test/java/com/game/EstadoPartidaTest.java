package com.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstadoPartidaTest {
    @Test
    void comecaComPlacarZeradoENoventaSegundos() {
        EstadoPartida estado = new EstadoPartida();

        assertEquals(0, estado.getGolsP1());
        assertEquals(0, estado.getGolsP2());
        assertEquals("01:30", estado.getTempoFormatado());
        assertFalse(estado.terminou());
    }

    @Test
    void terminaQuandoUmJogadorChegaACincoGols() {
        EstadoPartida estado = new EstadoPartida();

        for (int i = 0; i < EstadoPartida.LIMITE_GOLS; i++) estado.registrarGol(2);

        assertTrue(estado.terminou());
        assertEquals(2, estado.getVencedor());
    }

    @Test
    void cronometroNuncaFicaNegativoEPodeTerminarEmpatado() {
        EstadoPartida estado = new EstadoPartida();
        estado.registrarGol(1);
        estado.registrarGol(2);

        estado.atualizar(200);

        assertEquals("00:00", estado.getTempoFormatado());
        assertTrue(estado.terminou());
        assertEquals(0, estado.getVencedor());
    }

    @Test
    void rejeitaNumeroDeJogadorInvalido() {
        EstadoPartida estado = new EstadoPartida();
        assertThrows(IllegalArgumentException.class, () -> estado.registrarGol(3));
    }
}
