package it.epicode.endgame.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateVideogiocoRequest {
        private String titolo;
        private Integer annoDiUscita;
        private String genere;
        private LocalDate dataDiUscita;
        private String console;
        private String publisher;
        private String teamDiSviluppo;
        private String paese;
        private Integer metascore;
        private String premi;
        private String plot;
        private String trailer;
        private String recensione;
}
