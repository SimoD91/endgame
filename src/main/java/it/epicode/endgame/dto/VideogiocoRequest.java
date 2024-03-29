package it.epicode.endgame.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VideogiocoRequest {
    @NotBlank(message = "Titolo obbligatorio")
    private String titolo;
    @NotNull(message = "Anno di uscita obbligatorio")
    private int annoDiUscita;
    @NotBlank(message = "Genere obbligatorio")
    private String genere;
    @NotNull(message = "Data di uscita obbligatoria")
    private LocalDate dataDiUscita;
    @NotNull(message = "Console obbligatoria")
    private String console;
    @NotNull(message = "Publisher obbligatorio")
    private String publisher;
    @NotBlank(message = "Team di sviluppo obbligatorio")
    private String teamDiSviluppo;
    @NotBlank(message = "Paese obbligatorio")
    private String paese;
    @NotNull(message = "Metascore obbligatorio")
    private int metascore;
    @NotNull(message = "Premi obbligatorio")
    private String premi;
    @NotBlank(message = "Plot obbligatorio")
    private String plot;
    @NotBlank(message = "Trailer obbligatorio")
    private String trailer;
    @NotBlank(message = "Recensione obbligatoria")
    private String recensione;
}
