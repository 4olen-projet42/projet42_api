package dev.ort.spring.projet42.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Document {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String nom;

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
}
