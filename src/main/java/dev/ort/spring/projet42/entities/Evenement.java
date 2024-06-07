package dev.ort.spring.projet42.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Evenement {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String nom;

    @Column
    private String image;

    @Column
    @NotBlank
    private int maxParticipants;

    @Column
    private LocalDate dateDevut;

    @Column
    private String parcoursJSON;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getNom() {
        return nom;
    }

    public void setNom(@NotBlank String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NotBlank
    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(@NotBlank int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDate getDateDevut() {
        return dateDevut;
    }

    public void setDateDevut(LocalDate dateDevut) {
        this.dateDevut = dateDevut;
    }

    public String getParcoursJSON() {
        return parcoursJSON;
    }

    public void setParcoursJSON(String parcoursJSON) {
        this.parcoursJSON = parcoursJSON;
    }
}
