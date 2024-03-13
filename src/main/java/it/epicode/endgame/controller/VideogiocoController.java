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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
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
        return videogiocoService.getAllVideogiochi(pageable);
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
}
