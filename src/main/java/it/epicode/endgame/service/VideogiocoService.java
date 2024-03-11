package it.epicode.endgame.service;

import it.epicode.endgame.dto.UpdateVideogiocoRequest;
import it.epicode.endgame.dto.VideogiocoRequest;
import it.epicode.endgame.exception.NotFoundException;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.repository.VideogiocoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VideogiocoService {
    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @Autowired
    private UtenteService utenteService;

    public Page<Videogioco> getAllVideogiochi(Pageable pageable) {
        return videogiocoRepository.findAll(pageable);
    }

    public Videogioco getVideogiocoById(int id) throws NotFoundException {
        return videogiocoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Videogioco non trovato"));
    }

    public Videogioco saveVideogioco(VideogiocoRequest videogiocoRequest) {
        Videogioco videogioco = new Videogioco();
        videogioco.setTitolo(videogiocoRequest.getTitolo());
        videogioco.setAnnoDiUscita(videogiocoRequest.getAnnoDiUscita());
        videogioco.setGenere(videogiocoRequest.getGenere());
        videogioco.setDataDiUscita(videogiocoRequest.getDataDiUscita());
        videogioco.setTeamDiSviluppo(videogiocoRequest.getTeamDiSviluppo());
        videogioco.setPaese(videogiocoRequest.getPaese());
        videogioco.setMetascore(videogiocoRequest.getMetascore());
        videogioco.setPlot(videogiocoRequest.getPlot());
        videogioco.setPoster(videogiocoRequest.getPoster());
        videogioco.setImmagini(videogiocoRequest.getImmagini());
        videogioco.setTrailer(videogiocoRequest.getTrailer());
        videogioco.setRecensione(videogiocoRequest.getRecensione());
        return videogiocoRepository.save(videogioco);
    }
    public void deleteVideogioco(int id) {
        Videogioco videogioco = getVideogiocoById(id);
        videogiocoRepository.delete(videogioco);
    }
    public Videogioco updateVideogioco(int id, VideogiocoRequest videogiocoRequest) {
        Videogioco videogioco = getVideogiocoById(id);
        videogioco.setTitolo(videogiocoRequest.getTitolo());
        videogioco.setAnnoDiUscita(videogiocoRequest.getAnnoDiUscita());
        videogioco.setGenere(videogiocoRequest.getGenere());
        videogioco.setDataDiUscita(videogiocoRequest.getDataDiUscita());
        videogioco.setTeamDiSviluppo(videogiocoRequest.getTeamDiSviluppo());
        videogioco.setPaese(videogiocoRequest.getPaese());
        videogioco.setMetascore(videogiocoRequest.getMetascore());
        videogioco.setPlot(videogiocoRequest.getPlot());
        videogioco.setPoster(videogiocoRequest.getPoster());
        videogioco.setImmagini(videogiocoRequest.getImmagini());
        videogioco.setTrailer(videogiocoRequest.getTrailer());
        videogioco.setRecensione(videogiocoRequest.getRecensione());
        return videogiocoRepository.save(videogioco);
    }
    public Videogioco updateVideogiocoPatch(int id, UpdateVideogiocoRequest updateVideogiocoRequest) {
        Videogioco videogioco = getVideogiocoById(id);

        if (updateVideogiocoRequest.getTitolo() != null) {
            videogioco.setTitolo(updateVideogiocoRequest.getTitolo());
        }
        if (updateVideogiocoRequest.getAnnoDiUscita() != null) {
            videogioco.setAnnoDiUscita(updateVideogiocoRequest.getAnnoDiUscita());
        }
        if (updateVideogiocoRequest.getGenere() != null) {
            videogioco.setGenere(updateVideogiocoRequest.getGenere());
        }
        if (updateVideogiocoRequest.getDataDiUscita() != null) {
            videogioco.setDataDiUscita(updateVideogiocoRequest.getDataDiUscita());
        }
        if (updateVideogiocoRequest.getTeamDiSviluppo() != null) {
            videogioco.setTeamDiSviluppo(updateVideogiocoRequest.getTeamDiSviluppo());
        }
        if (updateVideogiocoRequest.getPaese() != null) {
            videogioco.setPaese(updateVideogiocoRequest.getPaese());
        }
        if (updateVideogiocoRequest.getMetascore() != null) {
            videogioco.setMetascore(updateVideogiocoRequest.getMetascore());
        }
        if (updateVideogiocoRequest.getPlot() != null) {
            videogioco.setPlot(updateVideogiocoRequest.getPlot());
        }
        if (updateVideogiocoRequest.getPoster() != null) {
            videogioco.setPoster(updateVideogiocoRequest.getPoster());
        }
        if (updateVideogiocoRequest.getImmagini() != null) {
            videogioco.setImmagini(updateVideogiocoRequest.getImmagini());
        }
        if (updateVideogiocoRequest.getTrailer() != null) {
            videogioco.setTrailer(updateVideogiocoRequest.getTrailer());
        }
        if (updateVideogiocoRequest.getRecensione() != null) {
            videogioco.setRecensione(updateVideogiocoRequest.getRecensione());
        }

        return videogiocoRepository.save(videogioco);
    }
    //metodo inutile?
    public Page<Videogioco> findByUtente(int id, Pageable pageable) {
        Utente utente = utenteService.getUtenteById(id);
        return videogiocoRepository.findByUtente(utente, pageable);
    }
}
