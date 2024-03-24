package it.epicode.endgame.controller;

import com.cloudinary.Cloudinary;
import it.epicode.endgame.dto.UpdateVideogiocoRequest;
import it.epicode.endgame.dto.VideogiocoRequest;
import it.epicode.endgame.exception.BadRequestException;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.repository.UtenteRepository;
import it.epicode.endgame.service.UtenteService;
import it.epicode.endgame.service.VideogiocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VideogiocoController {
    @Autowired
    private VideogiocoService videogiocoService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/videogiochi/get")
    public Page<Videogioco> getAll(Pageable pageable) {
        return videogiocoService.getAllVideogiochiOrderByTitoloAsc(pageable);
    }

    @GetMapping("/videogiochi/get/{id}")
    public Videogioco getVideogiocoById(@PathVariable int id) {
        return videogiocoService.getVideogiocoById(id);
    }

    @PutMapping("/videogiochi/{id}")
    public Videogioco updateVideogioco(@PathVariable int id, @RequestBody @Validated VideogiocoRequest videogiocoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return videogiocoService.updateVideogioco(id, videogiocoRequest);
    }

    @PatchMapping("/videogiochi/{id}")
    public Videogioco updateVideogiocoPatch(@PathVariable int id, @RequestBody @Validated UpdateVideogiocoRequest updateVideogiocoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return videogiocoService.updateVideogiocoPatch(id, updateVideogiocoRequest);
    }

    @PostMapping("/videogiochi")
    public Videogioco saveVideogioco(@RequestBody @Validated VideogiocoRequest videogiocoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return videogiocoService.saveVideogioco(videogiocoRequest);
    }

    @DeleteMapping("/videogiochi/{id}")
    public String deleteVideogioco(@PathVariable int id) {
        List<Utente> utenti = utenteRepository.findAll();
        for (Utente utente : utenti) {
            utenteService.removePreferitiUtente(utente.getIdUtente(), id);
        }
        videogiocoService.deleteVideogioco(id);
        return "Videogioco cancellato";
    }

    @GetMapping("/videogiochi/find/utente")
    public Page<Videogioco> findVideogiochiByUtente(@RequestParam("id_utente") int id ,Pageable pageable) {
        return (Page<Videogioco>) videogiocoService.findByUtente(id, pageable);
    }
    @PatchMapping("/videogiochi/{id}/upload-cover")
    public Videogioco uploadCover(@PathVariable int id, @RequestParam("upload") MultipartFile file) throws IOException {
        return videogiocoService.uploadCover(id,
                (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url"));
    }
    @PatchMapping("/videogiochi/{id}/upload-image")
    public Videogioco uploadImmagini(@PathVariable int id, @RequestParam("upload") MultipartFile file) throws IOException {
        return videogiocoService.aggiungiImmagini(id,
                (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url"));
    }
    @GetMapping("/videogiochi/get/sorted/titolo")
    public ResponseEntity<?> getVideogiochiByTitolo(@RequestParam(value = "titolo", required = false) String titolo, Pageable pageable) {
        if (titolo != null) {
        Page<Videogioco> videogiochi = videogiocoService.getVideogiochiByTitolo(titolo, pageable);
        if (videogiochi.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun videogioco trovato con il titolo " + titolo);
        } else {
            return ResponseEntity.ok(videogiochi);
        }
        } else {
            return ResponseEntity.badRequest().body("Cerca su Endgame digitando il titolo sulla barra di ricerca nella parte superiore della pagina.");
        }
    }
    @GetMapping("/videogiochi/get/sorted/anno")
    public ResponseEntity<?> getVideogiochiByAnnoDiUscita(@RequestParam(value = "annoDiUscita", required = false) Integer annoDiUscita, Pageable pageable) {
        if (annoDiUscita != null) {
            if (annoDiUscita <= 0) {
                return ResponseEntity.badRequest().body("Anno di uscita non valido.");
            }

            Page<Videogioco> videogiochi = videogiocoService.getVideogiochiByAnnoDiUscita(annoDiUscita, pageable);
            if (videogiochi.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun videogioco trovato per l'anno di uscita richiesto.");
            } else {
                return ResponseEntity.ok(videogiochi);
            }
        } else {
            return ResponseEntity.badRequest().body("Cerca su Endgame digitando l'anno di uscita sulla barra di ricerca nella parte superiore della pagina.");
        }
    }
    @GetMapping("/videogiochi/get/sorted/genere")
    public ResponseEntity<?> getVideogiochiByGenere(@RequestParam(value = "genere", required = false) String genere, Pageable pageable) {
        if (genere != null) {
            Page<Videogioco> videogiochi = videogiocoService.getVideogiochiByGenere(genere, pageable);
            if (videogiochi.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun videogioco trovato per il genere selezionato.");
            } else {
                return ResponseEntity.ok(videogiochi);
            }
        } else {
            return ResponseEntity.badRequest().body("Cerca su Endgame selezionando il genere sulla barra di ricerca nella parte superiore della pagina.");
        }
    }
    @GetMapping("/videogiochi/get/sorted/console")
    public ResponseEntity<?> getVideogiochiByConsole(@RequestParam(value = "console", required = false) String console, Pageable pageable) {
        if (console != null) {
            Page<Videogioco> videogiochi = videogiocoService.getVideogiochiByConsole(console, pageable);
            if (videogiochi.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun videogioco trovato per la piattaforma selezionata.");
            } else {
                return ResponseEntity.ok(videogiochi);
            }
        } else {
            return ResponseEntity.badRequest().body("Cerca su Endgame selezionando la piattaforma sulla barra di ricerca nella parte superiore della pagina.");
        }
    }
    @GetMapping("/videogiochi/get/sorted/bestmetascore")
    public ResponseEntity<?> getVideogiochiByMetascoreGreaterThan(
            @RequestParam(value = "metascore", required = false, defaultValue = "89") int metascore,
            Pageable pageable
    ) {
        Page<Videogioco> videogiochi = videogiocoService.getVideogiochiByBestMetascore(metascore, pageable);
        if (videogiochi.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun videogioco trovato.");
        } else {
            return ResponseEntity.ok(videogiochi);
        }
    }
}
