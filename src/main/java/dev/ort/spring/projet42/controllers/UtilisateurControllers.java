package dev.ort.spring.projet42.controllers;


import dev.ort.spring.projet42.entities.Utilisateur;
import dev.ort.spring.projet42.repositories.UtilisateurRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurControllers {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/connected")
    public String getUtilisateurName(Authentication authentication) {

        return authentication.getName();
    }

    @PutMapping
    public Utilisateur createOrUpdate(@RequestBody @Valid Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }
}
