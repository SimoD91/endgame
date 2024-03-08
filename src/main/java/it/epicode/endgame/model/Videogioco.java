package it.epicode.endgame.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Videogioco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_videogioco")
    private int idVideogioco;
    private String titolo;
    private int annoDiUscita;
    private String genere;
    private LocalDate dataDiUscita;
    private String teamDiSviluppo;
    private String paese;
    private int metascore;
    private String plot;
    private String poster;
    private String immagini;
    private String trailer;
    private String recensione;

    @OneToMany(mappedBy = "videogioco", cascade = CascadeType.ALL)
    private List<Preferiti> preferiti = new ArrayList<>();
}
