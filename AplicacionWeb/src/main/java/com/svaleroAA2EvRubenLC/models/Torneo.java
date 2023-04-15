package com.svaleroAA2EvRubenLC.models;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Torneo {
    private int id_torneo;
    private int id_club;
    private int id_juego;

    private LocalDate fecha;
    @NonNull
    private String hora_init;
    @NonNull
    private int num_plazas;

    /*private Club club;
    private Juego juego;*/
}
