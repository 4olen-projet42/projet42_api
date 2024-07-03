package dev.ort.spring.projet42.controllers;
import dev.ort.spring.projet42.entities.Evenement;
import dev.ort.spring.projet42.exceptions.ResourceNotFoundException;
import dev.ort.spring.projet42.repositories.EvenementRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evenements")
public class EvenementControllers {

    private static final Logger logger = LoggerFactory.getLogger(Evenement.class);


    @Autowired
    private EvenementRepository evenementRepository;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    @PutMapping
    public Evenement createOrUpdate(@RequestBody @Valid  Evenement evenement) {
        if(evenementRepository.existsById(evenement.getId())){
            logger.info("Updating evenement... {}", evenement.getId());
        } else {
            logger.info("Creating evenement... {}", evenement.getId());
        }
        return evenementRepository.save(evenement);
    }

    @Operation(summary = "Récupération d'un événement à partir de son identifiant")
    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Evenement getEvenementsById(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {

        Optional<Evenement> event = evenementRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        }
        throw new ResourceNotFoundException(Evenement.class, id);

    }

    @Operation(summary = "Récupération de tous les événements avec une dates de début supérieure ou égale à la date du jour")
    @ApiResponse(responseCode = "404", description = "Aucun événement disponible")
    @RequestMapping(path = "/allAvailable", method = RequestMethod.GET)
    public List<Evenement> getAllAvailableEvenements() {
        return evenementRepository.findByDateDebutGreaterThanEqual(LocalDate.now());
    }

    @Operation(summary = "Récupération des 3 plus récent événements ")
    @ApiResponse(responseCode = "404", description = "Aucun événement disponible")
    @RequestMapping(path = "/newEvent", method = RequestMethod.GET)
    public List<Evenement> getNewEvent() {
        return evenementRepository.findNewEvenement();
    }

    @Operation(summary = "Recherche d'événements par mot clé")
    @ApiResponse(responseCode = "404", description = "Aucun événement trouvé")
    @RequestMapping(path = "/like/{search}", method = RequestMethod.GET)
    public List<Evenement> search(@PathVariable(name = "search") String search) {
        return evenementRepository.searchEvenements(search);
    }

    @Operation(summary = "Recherche des évènements auquel l'utilisateur est inscrit")
    @ApiResponse(responseCode = "404", description = "Aucun événement trouvé")
    @RequestMapping(path = "/api/byUser", method = RequestMethod.GET)
    public List<Evenement> searchByUser(Authentication authentication) {

        Jwt jwt = (Jwt) authentication.getPrincipal();

        return evenementRepository.findEvenementsByUtilisateur(jwt.getClaim("sub").toString());
    }

}
