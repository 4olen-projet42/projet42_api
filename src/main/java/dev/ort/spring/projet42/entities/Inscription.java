package dev.ort.spring.projet42.entities;

import jakarta.persistence.*;

@Entity
public class Inscription {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private byte valide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte getValide() {
        return valide;
    }

    public void setValide(byte valide) {
        this.valide = valide;
    }
}
