package dev.ort.spring.projet42.controllers;

import dev.ort.spring.projet42.entities.Inscription;
import dev.ort.spring.projet42.repositories.InscriptionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionControllers {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Inscription> getAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    @PutMapping
    public Inscription createOrUpdate(@RequestBody @Valid  Inscription inscription, Authentication authentication) {

        Jwt jwt = (Jwt) authentication.getPrincipal();

        inscription.setIdUtilisateur(jwt.getClaim("sub").toString());

        if(inscriptionRepository.existsById(inscription.getId())){
            System.out.println("Updating inscription... " + inscription.getId());
        } else {
            System.out.println("Creating inscription... " + inscription.getId());
        }
        return inscriptionRepository.save(inscription);
    }

}
