package dev.ort.spring.projet42.repositories;

import dev.ort.spring.projet42.entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    List<Evenement> findByDateDebutGreaterThanEqual(LocalDate date);

    @Query("SELECT e FROM Evenement e WHERE e.dateDebut >= CURRENT_DATE ORDER BY e.dateDebut DESC LIMIT 3")
    List<Evenement> findNewEvenement();

    @Query("SELECT e FROM Evenement e WHERE e.nom LIKE %:search%")
    List<Evenement> searchEvenements(@Param("search") String search);
}