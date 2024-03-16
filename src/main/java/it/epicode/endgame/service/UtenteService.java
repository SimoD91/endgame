package it.epicode.endgame.service;

import it.epicode.endgame.dto.UpdateUtenteRequest;
import it.epicode.endgame.dto.UtenteRequest;
import it.epicode.endgame.exception.NotFoundException;
import it.epicode.endgame.model.Tipologia;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.repository.UtenteRepository;
import it.epicode.endgame.repository.VideogiocoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private VideogiocoRepository videogiocoRepository;

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
    public List<Utente> getAllUtentiOrderedByTipologia() {
        return utenteRepository.findAllByOrderByTipologiaAsc();
    }
    public Utente updateUtente(int id, UtenteRequest utenteRequest){
        Utente utente = getUtenteById(id);

        utente.setUsername(utenteRequest.getUsername());
        utente.setPassword(encoder.encode(utenteRequest.getPassword()));
        utente.setEmail(utenteRequest.getEmail());
        utente.setNome(utenteRequest.getNome());
        utente.setCognome(utenteRequest.getCognome());

        return utenteRepository.save(utente);
    }

    public Utente updateUtentePatch(int id, UpdateUtenteRequest updateUtenteRequest) {
        Utente utente = getUtenteById(id);

        if (updateUtenteRequest.getUsername() != null) {
            utente.setUsername(updateUtenteRequest.getUsername());
        }
        if (updateUtenteRequest.getPassword() != null) {
            utente.setPassword(encoder.encode(updateUtenteRequest.getPassword()));
        }
        if (updateUtenteRequest.getEmail() != null) {
            utente.setEmail(updateUtenteRequest.getEmail());
        }
        if (updateUtenteRequest.getNome() != null) {
            utente.setNome(updateUtenteRequest.getNome());
        }
        if (updateUtenteRequest.getCognome() != null) {
            utente.setCognome(updateUtenteRequest.getCognome());
        }
        return utenteRepository.save(utente);
    }

    public Utente updateTipologiaUtente(int id,String tipologia){
        Utente utente = getUtenteById(id);
        utente.setTipologia(Tipologia.valueOf(tipologia));
        return utenteRepository.save(utente);
    }

    public void deleteUtente(int id) {
        try {
            utenteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return;
        }
        if (utenteRepository.existsById(id)) {
            throw new NotFoundException("Utente non trovato.");
        }
    }


    public Utente savePreferitiUtente(int idUtente, int idVideogioco) {
        Utente utente = getUtenteById(idUtente);
        Videogioco videogioco = videogiocoRepository.findById(idVideogioco).orElse(null);
        if (videogioco != null) {
            utente.getPreferiti().add(videogioco);
            utenteRepository.save(utente);
            return utente;
        } else {
            return null;
        }
    }

    public Page<Videogioco> findPaginatedPreferitiUtente(int idUtente, Pageable pageable) {
        return utenteRepository.findPreferitiById(idUtente, pageable);
    }

    public Utente removePreferitiUtente(int idUtente, int idVideogioco) {
        Utente utente = getUtenteById(idUtente);
        if (utente != null) {
            Videogioco videogiocoToRemove = null;
            for (Videogioco videogioco : utente.getPreferiti()) {
                if (videogioco.getIdVideogioco() == idVideogioco) {
                    videogiocoToRemove = videogioco;
                    break;
                }
            }
            if (videogiocoToRemove != null) {
                utente.getPreferiti().remove(videogiocoToRemove);
                utenteRepository.save(utente);
                return utente;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public Utente uploadAvatar(int id, String url) {
        Utente utente = getUtenteById(id);
        utente.setAvatar(url);
        return utenteRepository.save(utente);
    }
}