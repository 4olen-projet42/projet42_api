package dev.ort.spring.projet42.controllers;
import dev.ort.spring.projet42.entities.Sport;
import dev.ort.spring.projet42.repositories.SportRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SportControllers {

    @Autowired
    private SportRepository sportRepository;

    @Operation(summary = "Récupération de tous les sports")
    @RequestMapping(path = "/sports", method = RequestMethod.GET)
    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    @Operation(summary = "Création ou mise à jour d'un sport")
    @PutMapping("/api/sports")
    public Sport createOrUpdate(@RequestBody @Valid Sport sport) {
        if(sportRepository.existsById(sport.getId())){
            System.out.println("Updating sport... " + sport.getId());
        } else {
            System.out.println("Creating sport... " + sport.getId());
        }
        return sportRepository.save(sport);
    }

}
