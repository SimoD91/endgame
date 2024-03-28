package it.epicode.endgame.service;

import it.epicode.endgame.dto.UpdateVideogiocoRequest;
import it.epicode.endgame.dto.VideogiocoRequest;
import it.epicode.endgame.exception.NotFoundException;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.repository.VideogiocoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideogiocoService {
    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @Autowired
    private UtenteService utenteService;

    public Page<Videogioco> getAllVideogiochiOrderByTitoloAsc(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return videogiocoRepository.findAllByOrderByTitoloAsc(pageable);
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
        videogioco.setConsole(videogiocoRequest.getConsole());
        videogioco.setPublisher(videogiocoRequest.getPublisher());
        videogioco.setTeamDiSviluppo(videogiocoRequest.getTeamDiSviluppo());
        videogioco.setPaese(videogiocoRequest.getPaese());
        videogioco.setMetascore(videogiocoRequest.getMetascore());
        videogioco.setPremi(videogiocoRequest.getPremi());
        videogioco.setPlot(videogiocoRequest.getPlot());
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
        videogioco.setConsole(videogiocoRequest.getConsole());
        videogioco.setPublisher(videogiocoRequest.getPublisher());
        videogioco.setTeamDiSviluppo(videogiocoRequest.getTeamDiSviluppo());
        videogioco.setPaese(videogiocoRequest.getPaese());
        videogioco.setMetascore(videogiocoRequest.getMetascore());
        videogioco.setPremi(videogiocoRequest.getPremi());
        videogioco.setPlot(videogiocoRequest.getPlot());
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
        if (updateVideogiocoRequest.getConsole() != null) {
            videogioco.setConsole(updateVideogiocoRequest.getConsole());
        }
        if (updateVideogiocoRequest.getPublisher() != null) {
            videogioco.setPublisher(updateVideogiocoRequest.getPublisher());
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
        if (updateVideogiocoRequest.getPremi() != null) {
            videogioco.setPremi(updateVideogiocoRequest.getPremi());
        }
        if (updateVideogiocoRequest.getPlot() != null) {
            videogioco.setPlot(updateVideogiocoRequest.getPlot());
        }
        if (updateVideogiocoRequest.getTrailer() != null) {
            videogioco.setTrailer(updateVideogiocoRequest.getTrailer());
        }
        if (updateVideogiocoRequest.getRecensione() != null) {
            videogioco.setRecensione(updateVideogiocoRequest.getRecensione());
        }
        return videogiocoRepository.save(videogioco);
    }

    public Videogioco uploadCover(int id, String url) {
        Videogioco videogioco = getVideogiocoById(id);
        videogioco.setCover(url);
        return videogiocoRepository.save(videogioco);
    }

    public Videogioco aggiungiImmagini(int id, String url) {
        Optional<Videogioco> optionalVideogioco = videogiocoRepository.findById(id);
        if (optionalVideogioco.isPresent()) {
            Videogioco videogioco = optionalVideogioco.get();
            videogioco.getImmagini().add(url);
            return videogiocoRepository.save(videogioco);
        } else {
            throw new IllegalArgumentException("Videogioco non trovato con ID: " + id);
        }
    }

    public Page<Videogioco> getVideogiochiByTitolo(String titolo, Pageable pageable) {
        return videogiocoRepository.findByTitoloContainingIgnoreCase(titolo, pageable);
    }

    public Page<Videogioco> getVideogiochiByAnnoDiUscita(int annoDiUscita, Pageable pageable) {
        return videogiocoRepository.findByAnnoDiUscita(annoDiUscita, pageable);
    }

    public Page<Videogioco> getVideogiochiByGenere(String genere, Pageable pageable) {
        return videogiocoRepository.findByGenere(genere, pageable);
    }

    public Page<Videogioco> getVideogiochiByConsole(String console, Pageable pageable) {
        return videogiocoRepository.findByConsole(console, pageable);
    }

    public Page<Videogioco> getVideogiochiByBestMetascore(int metascore, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return videogiocoRepository.findByBestMetascore(metascore, pageable);
    }

    public Page<Videogioco> findByUtente(int id, Pageable pageable) {
        Utente utente = utenteService.getUtenteById(id);
        return videogiocoRepository.findByUtente(utente, pageable);
    }

    public Page<Videogioco> getVideogiochiByTitoloEGenere(String titolo, String genere, Pageable pageable) {
        return videogiocoRepository.findByTitoloEGenere(titolo, genere, pageable);
    }

    public Page<Videogioco> getPaginatedGames(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        return videogiocoRepository.findAll(pageable);
    }
}
