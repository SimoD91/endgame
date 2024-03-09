package it.epicode.endgame.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
@Data
public class VideogiocoRequest {
    @NotBlank(message = "Titolo obbligatorio")
    private String titolo;
    @NotBlank(message = "Anno di uscita obbligatorio")
    private int annoDiUscita;
    @NotBlank(message = "Genere obbligatorio")
    private String genere;
    @NotBlank(message = "Data di uscita obbligatoria")
    private LocalDate dataDiUscita;
    @NotBlank(message = "Team di sviluppo obbligatorio")
    private String teamDiSviluppo;
    @NotBlank(message = "Paese obbligatorio")
    private String paese;
    @NotBlank(message = "Metascore obbligatorio")
    private int metascore;
    @NotBlank(message = "Plot obbligatorio")
    private String plot;
    @NotBlank(message = "Poster obbligatorio")
    private String poster;
    @NotBlank(message = "Immagini obbligatorie")
    private String immagini;
    @NotBlank(message = "Trailer obbligatorio")
    private String trailer;
    @NotBlank(message = "Recensione obbligatoria")
    private String recensione;
}
