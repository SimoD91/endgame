package it.epicode.endgame.dto;

import lombok.Data;

@Data
public class UpdateUtenteRequest {
    private String username;
    private String password;
    private String email;
    private String nome;
    private String cognome;
}
