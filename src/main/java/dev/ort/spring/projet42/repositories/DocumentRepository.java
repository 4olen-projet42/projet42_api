package dev.ort.spring.projet42.repositories;

import dev.ort.spring.projet42.entities.Document;
import dev.ort.spring.projet42.entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    //@Query("SELECT d FROM Document d WHERE d.idUtilisateur = :idUtilisateur")
    //List<Document> findDocumentByUtilisateur(@Param("idUtilisateur") String idUtilisateur);

    List<Document> findByIdUtilisateur(@Param("idUtilisateur") String idUtilisateur);
}
