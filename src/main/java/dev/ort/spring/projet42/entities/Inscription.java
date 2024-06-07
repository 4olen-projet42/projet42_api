package dev.ort.spring.projet42.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Inscription {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer valide;

    @OneToOne
    @JoinColumn(name = "evenement_id")
    @NotNull
    private Evenement evenement;

    @Column
    @NotNull
    private Long idUtilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValide() {
        return valide;
    }

    public void setValide(Integer valide) {
        this.valide = valide;
    }

    public @NotNull Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(@NotNull Evenement evenement) {
        this.evenement = evenement;
    }

    public @NotNull Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(@NotNull Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
