package it.epicode.endgame.service;

import it.epicode.endgame.dto.UtenteRequest;
import it.epicode.endgame.exception.NotFoundException;
import it.epicode.endgame.model.Tipologia;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private VideogiocoService videogiocoService;

    public Utente saveUtente(UtenteRequest utenteRequest) {
        Utente utente = new Utente();

        utente.setUsername(utenteRequest.getUsername());
        utente.setPassword(encoder.encode(utenteRequest.getPassword()));
        utente.setNome(utenteRequest.getNome());
        utente.setCognome(utenteRequest.getCognome());
        utente.setEmail(utenteRequest.getEmail());
        utente.setTipologia(Tipologia.USER);

        return utenteRepository.save(utente);
    }
    public Utente getUtenteById(int id){
        return utenteRepository.findById(id).orElseThrow(()->new NotFoundException("Utente non trovato"));
    }

    public Utente getUtenteByUsername(String username){
        return utenteRepository.findByUsername(username).orElseThrow(()->new NotFoundException("Username non trovato"));
    }

    public List<Utente> getAllUtenti(){
        return utenteRepository.findAll();
    }
    public Utente updateUtente(int id, UtenteRequest utenteRequest){
        Utente utente = getUtenteById(id);

        utente.setUsername(utenteRequest.getUsername());
        utente.setPassword(utenteRequest.getPassword());
        utente.setEmail(utenteRequest.getEmail());
        utente.setNome(utenteRequest.getNome());
        utente.setCognome(utenteRequest.getCognome());

        return utenteRepository.save(utente);
    }

    public Utente updateTipologiaUtente(int id,String tipologia){
        Utente utente = getUtenteById(id);
        utente.setTipologia(Tipologia.valueOf(tipologia));
        return utenteRepository.save(utente);
    }

    public void deleteUtente(int id){
        if (!utenteRepository.existsById(id)) {
            throw new NotFoundException("Utente non trovato");
        }
        utenteRepository.deleteById(id);
    }

    public Utente savePreferitiUtente(int idUtente, int idVideogioco){
        Utente utente = getUtenteById(idUtente);
        Videogioco videogioco = videogiocoService.getVideogiocoById(idVideogioco);
        utente.getPreferiti().add(videogioco);
        return utenteRepository.save(utente);
    }
}