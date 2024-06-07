package dev.ort.spring.projet42.controllers;
import dev.ort.spring.projet42.entities.Evenement;
import dev.ort.spring.projet42.repositories.EvenementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evenements")
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
}
