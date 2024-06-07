package dev.ort.spring.projet42.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

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
    @NotNull
    @Positive
    private Integer maxParticipants;

    @Column
    private LocalDate dateDevut;

    @Column
    private String parcoursJSON;

    @Column
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "evenement_sport",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_id"))
    private List<Sport> sports;

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

    public @NotNull Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(@NotNull Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getParcoursJSON() {
        return parcoursJSON;
    }

    public void setParcoursJSON(String parcoursJSON) {
        this.parcoursJSON = parcoursJSON;
    }

    public LocalDate getDateDevut() {
        return dateDevut;
    }

    public void setDateDevut(LocalDate dateDevut) {
        this.dateDevut = dateDevut;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
}
