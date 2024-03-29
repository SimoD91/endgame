package it.epicode.endgame.controller;

import it.epicode.endgame.dto.LoginRequest;
import it.epicode.endgame.dto.UtenteRequest;
import it.epicode.endgame.exception.BadRequestException;
import it.epicode.endgame.exception.LoginFaultException;
import it.epicode.endgame.model.Utente;
import it.epicode.endgame.security.JwtTools;
import it.epicode.endgame.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private PasswordEncoder encoder;
    @PostMapping("/auth/register")
    public Utente register(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return utenteService.saveUtente(utenteRequest);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        Utente utente = utenteService.getUtenteByUsername(loginRequest.getUsername());

        if (encoder.matches(loginRequest.getPassword(), utente.getPassword())) {
            String token = jwtTools.createToken(utente);
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } else {
            throw new LoginFaultException("Username/Password errate");
        }
    }
}
