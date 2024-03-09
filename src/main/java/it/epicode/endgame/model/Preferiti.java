//package it.epicode.endgame.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//public class Preferiti {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
//
//    @ManyToOne
//    @JoinColumn(name = "id_utente")
//    private Utente utente;
//
//    @ManyToMany
//    @JoinColumn(name = "id_videogioco")
//    private Videogioco videogioco;
//
//    public Preferiti(Utente utente, Videogioco videogioco) {
//        this.utente = utente;
//        this.videogioco = videogioco;
//    }
//}