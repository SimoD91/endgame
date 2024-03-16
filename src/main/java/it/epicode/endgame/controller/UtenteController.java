package it.epicode.endgame.controller;

import com.cloudinary.Cloudinary;
import it.epicode.endgame.dto.UpdateUtenteRequest;
import it.epicode.endgame.dto.UtenteRequest;
import it.epicode.endgame.exception.BadRequestException;
import it.epicode.endgame.exception.UnAuthorizedException;
import it.epicode.endgame.model.Tipologia;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.model.Videogioco;
import it.epicode.endgame.security.JwtTools;
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
import java.util.Map;

@RestController
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private JwtTools jwtTools;
    @GetMapping("/utenti")
    public List<Utente> getAllUtentiOrderedByTipologia() {
        return utenteService.getAllUtentiOrderedByTipologia();
    }
    @GetMapping("/utenti/{id}")
    public Utente getUtenteById(@PathVariable int id){
        return utenteService.getUtenteById(id);
    }

    @PutMapping("/utenti/{id}")
    public Utente updateUtente(@PathVariable int id, @RequestHeader("Authorization") String token, @RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token non fornito o non valido.");
        }

        String jwt = token.substring(7);
        String userIdFromToken = jwtTools.extractUserIdFromToken(jwt);

        Utente utente = utenteService.getUtenteById(id);
        if (utente.getIdUtente() != Integer.parseInt(userIdFromToken)) {
            throw new UnAuthorizedException("L'utente non è autorizzato a modificare questo profilo.");
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
    public String deleteUtente(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token non fornito o non valido.");
        }

        String jwt = token.substring(7);
        String userIdFromToken = jwtTools.extractUserIdFromToken(jwt);

        Utente utente = utenteService.getUtenteById(id);
        if (utente.getIdUtente() != Integer.parseInt(userIdFromToken) && !isAdmin(token)) {
            throw new UnAuthorizedException("L'utente non è autorizzato a eliminare questo profilo.");
        }

        utenteService.deleteUtente(id);
        return "Utente cancellato";
    }
    private boolean isAdmin(String token) {
        String jwt = token.substring(7);
        String userIdFromToken = jwtTools.extractUserIdFromToken(jwt);
        Utente utente = utenteService.getUtenteById(Integer.parseInt(userIdFromToken));
        return utente.getTipologia() == Tipologia.ADMIN;
    }
    @PatchMapping("/utenti/{id}")
    public Utente updateUtentePatch(@PathVariable int id, @RequestHeader("Authorization") String token, @RequestBody @Validated UpdateUtenteRequest updateUtenteRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token non fornito o non valido.");
        }

        String jwt = token.substring(7);
        String userIdFromToken = jwtTools.extractUserIdFromToken(jwt);

        Utente utente = utenteService.getUtenteById(id);
        if (utente.getIdUtente() != Integer.parseInt(userIdFromToken)) {
            throw new UnAuthorizedException("L'utente non è autorizzato a modificare questo profilo.");
        }
        return utenteService.updateUtentePatch(id, updateUtenteRequest);
    }
    @PatchMapping("/utenti/tipologia/{id}")
    public Utente changeTipologia(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
        String tipologia = requestBody.get("tipologia");
        return utenteService.updateTipologiaUtente(id, tipologia);
    }
    @PatchMapping("/utenti/{idUtente}/preferiti/{idVideogioco}")
    public Utente aggiungiPreferito(@PathVariable int idUtente, @PathVariable int idVideogioco, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token non fornito o non valido.");
        }

        String jwt = token.substring(7);
        String userIdFromToken = jwtTools.extractUserIdFromToken(jwt);

        if (idUtente != Integer.parseInt(userIdFromToken)) {
            throw new UnAuthorizedException("L'utente non è autorizzato ad aggiungere preferiti per questo profilo.");
        }

        return utenteService.savePreferitiUtente(idUtente, idVideogioco);
    }
    @GetMapping("/utenti/{idUtente}/preferiti")
    public Page<Videogioco> getPreferitiPaginati(@PathVariable int idUtente, Pageable pageable, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token non fornito o non valido.");
        }

        String jwt = token.substring(7);
        String userIdFromToken = jwtTools.extractUserIdFromToken(jwt);

        if (idUtente != Integer.parseInt(userIdFromToken)) {
            throw new UnAuthorizedException("L'utente non è autorizzato a visualizzare i preferiti per questo profilo.");
        }

        return utenteService.findPaginatedPreferitiUtente(idUtente, pageable);
    }

    @DeleteMapping("/utenti/{idUtente}/preferiti/{idVideogioco}")
    public Utente rimuoviPreferito(@PathVariable int idUtente, @PathVariable int idVideogioco, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token non fornito o non valido.");
        }

        String jwt = token.substring(7);
        String userIdFromToken = jwtTools.extractUserIdFromToken(jwt);

        if (idUtente != Integer.parseInt(userIdFromToken)) {
            throw new UnAuthorizedException("L'utente non è autorizzato a rimuovere preferiti per questo profilo.");
        }

        return utenteService.removePreferitiUtente(idUtente, idVideogioco);
    }

    @PatchMapping("/utenti/{id}/upload")
    public Utente uploadAvatar(@PathVariable int id, @RequestParam("upload") MultipartFile file) throws IOException {
        return utenteService.uploadAvatar(id,
                (String) cloudinary.uploader().upload(file.getBytes(), new HashMap<>()).get("url"));
    }
}