package com.svaleroAA2EvRubenLC.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Club {
    private String id_club_pk;
    @NonNull
    private String nom_club;
    @NonNull
    private String nom_calle;
    @NonNull
    private String num_calle;
    @NonNull
    private String cp;
    @NonNull
    private String hor_apert;
    @NonNull
    private String hor_cierre;

}
