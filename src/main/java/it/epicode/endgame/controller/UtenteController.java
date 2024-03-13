package it.epicode.endgame.controller;

import com.cloudinary.Cloudinary;
import it.epicode.endgame.dto.UpdateUtenteRequest;
import it.epicode.endgame.dto.UpdateVideogiocoRequest;
import it.epicode.endgame.dto.UtenteRequest;
import it.epicode.endgame.exception.BadRequestException;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.service.UtenteService;
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
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private Cloudinary cloudinary;
    @GetMapping("/utenti")
    public List<Utente> getAll(){
        return utenteService.getAllUtenti();
    }

    @GetMapping("/utenti/{id}")
    public Utente getUtenteById(@PathVariable int id){
        return utenteService.getUtenteById(id);
    }

    @PutMapping("/utenti/{id}")
    public Utente updateUtente(@PathVariable int id, @RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return utenteService.updateUtente(id, utenteRequest);

    }
    @PostMapping("/utenti")
    public Utente save(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return utenteService.saveUtente(utenteRequest);
    }
    @DeleteMapping("/utenti/{id}")
    public void deleteUtente(@PathVariable int id){

        utenteService.deleteUtente(id);
    }
    @PatchMapping("/utenti/{id}")
    public Utente updateUtentePatch(@PathVariable int id, @RequestBody @Validated UpdateUtenteRequest updateUtenteRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return utenteService.updateUtentePatch(id, updateUtenteRequest);
    }
    @PatchMapping("/utenti/tipologia/{id}")
    public Utente changeTipologia(@PathVariable int id, @RequestBody String tipologia){
        return utenteService.updateTipologiaUtente(id, tipologia);
    }
    @PatchMapping("/utenti/{idUtente}/preferiti/{idVideogioco}")
    public Utente aggiungiPreferito(@PathVariable int idUtente, @PathVariable int idVideogioco) {
        return utenteService.savePreferitiUtente(idUtente, idVideogioco);
    }
    @GetMapping("/utenti/{idUtente}/preferiti")
    public Page<Videogioco> getPreferitiPaginati(@PathVariable int idUtente, Pageable pageable) {
        return utenteService.findPaginatedPreferitiUtente(idUtente, pageable);
    }
    @DeleteMapping("/utenti/{idUtente}/preferiti/{idVideogioco}")
    public Utente rimuoviPreferito(@PathVariable int idUtente, @PathVariable int idVideogioco) {
        return utenteService.removePreferitiUtente(idUtente, idVideogioco);
    }
    @PatchMapping("/utenti/{id}/upload")
    public Utente uploadAvatar(@PathVariable int id, @RequestParam("upload") MultipartFile file) throws IOException {
        return utenteService.uploadAvatar(id,
                (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url"));
    }
}