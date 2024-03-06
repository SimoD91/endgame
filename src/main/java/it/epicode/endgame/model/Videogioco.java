package it.epicode.endgame.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Videogioco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_videogioco")
    private int idVideogioco;
    private String titolo;
    private int anno;
    private String genere;
    private LocalDate uscita;
    private String teamDiSviluppo;
    private String paese;
    private int metascore;
    private String plot;
    private String poster;
    private String immagini;
    private String trailer;
    private String recensione;
}
