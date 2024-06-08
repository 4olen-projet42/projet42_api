package dev.ort.spring.projet42.controllers;
import dev.ort.spring.projet42.entities.Evenement;
import dev.ort.spring.projet42.exceptions.ResourceNotFoundException;
import dev.ort.spring.projet42.repositories.EvenementRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evenements")
public class EvenementControllers {

    @Autowired
    private EvenementRepository evenementRepository;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    @PutMapping
    public Evenement createOrUpdate(@RequestBody @Valid  Evenement evenement) {
        if(evenementRepository.existsById(evenement.getId())){
            System.out.println("Updating evenement... " + evenement.getId());
        } else {
            System.out.println("Creating evenement... " + evenement.getId());
        }
        return evenementRepository.save(evenement);
    }

    @Operation(summary = "Récupération d'un événement à partir de son identifiant")
    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Evenement get(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {

        Optional<Evenement> event = evenementRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        }
        throw new ResourceNotFoundException(Evenement.class, id);

    }
}
