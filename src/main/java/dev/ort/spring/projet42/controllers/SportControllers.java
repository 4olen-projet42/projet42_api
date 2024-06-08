package dev.ort.spring.projet42.controllers;
import dev.ort.spring.projet42.entities.Sport;
import dev.ort.spring.projet42.repositories.SportRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
public class SportControllers {

    @Autowired
    private SportRepository sportRepository;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    @PutMapping
    public Sport createOrUpdate(@RequestBody @Valid Sport sport) {
        if(sportRepository.existsById(sport.getId())){
            System.out.println("Updating sport... " + sport.getId());
        } else {
            System.out.println("Creating sport... " + sport.getId());
        }
        return sportRepository.save(sport);
    }

}
