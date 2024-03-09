package it.epicode.endgame.controller;

import it.epicode.endgame.dto.VideogiocoRequest;
import it.epicode.endgame.exception.BadRequestException;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.service.UtenteService;
import it.epicode.endgame.service.VideogiocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class VideogiocoController {
    @Autowired
    private VideogiocoService videogiocoService;
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/videogiochi")
    public Page<Videogioco> getAll(Pageable pageable){
        return videogiocoService.getAllVideogiochi(pageable);
    }

    @GetMapping("/videogiochi/{id}")
    public Videogioco getVideogiocoById(@PathVariable int id){
        return videogiocoService.getVideogiocoById(id);
    }

    @PutMapping("/videogiochi/{id}")
    public Videogioco updateVideogioco(@PathVariable int id, @RequestBody @Validated VideogiocoRequest videogiocoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return videogiocoService.updateVideogioco(id, videogiocoRequest);
    }

    @PostMapping("/videogiochi")
    public Videogioco saveVideogioco(@RequestBody @Validated VideogiocoRequest videogiocoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return videogiocoService.saveVideogioco(videogiocoRequest);
    }

    @DeleteMapping("/videogiochi/{id}")
    public void deleteVideogioco(@PathVariable int id) {
        videogiocoService.deleteVideogioco(id);
    }

    @GetMapping("/videogiochi/find/utente")
    public Page<Videogioco> findVideogiochiByUtente(@RequestParam("id_utente") int id ,Pageable pageable) {
        return (Page<Videogioco>) videogiocoService.findByUtente(id, pageable);
    }
}
