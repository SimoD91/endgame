package it.epicode.endgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Videogioco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "videogioco_sequence")
    @SequenceGenerator(name = "videogioco_sequence", initialValue = 1,allocationSize = 1)
    @Column(name = "id_videogioco")
    private int idVideogioco;
    private String titolo;
    private int annoDiUscita;
    private String genere;
    private LocalDate dataDiUscita;
    private String console;
    private String publisher;
    private String teamDiSviluppo;
    private String paese;
    private int metascore;
    private String plot;
    private String cover;
    private List<String> immagini = new ArrayList<>();
    private String trailer;
    private String recensione;

    @JsonIgnore
    @ManyToMany(mappedBy = "preferiti")
    private List<Utente> utente;

}
