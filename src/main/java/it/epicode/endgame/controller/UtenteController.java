package it.epicode.endgame.controller;

import it.epicode.endgame.dto.UtenteRequest;
import it.epicode.endgame.exception.BadRequestException;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
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
    public Utente changeTipologia(@PathVariable int id, @RequestBody String tipologia){
        return utenteService.updateTipologiaUtente(id, tipologia);
    }
}