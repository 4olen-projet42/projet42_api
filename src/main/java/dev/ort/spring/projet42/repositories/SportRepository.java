package dev.ort.spring.projet42.repositories;

import dev.ort.spring.projet42.entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
}
