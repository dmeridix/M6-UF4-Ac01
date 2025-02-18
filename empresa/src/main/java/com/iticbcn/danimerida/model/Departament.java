package com.iticbcn.danimerida.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Departament")
public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", unique = true)
    private String nom;

    @OneToMany(mappedBy = "departament", fetch = FetchType.EAGER)
    private Set<Empleat> empleats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Empleat> getEmpleats() {
        return empleats;
    }

    public void setEmpleats(Set<Empleat> empleats) {
        this.empleats = empleats;
    }

    // toString per modificar la sortida per pantalla de forma controlada
    // (en aquesta part he necessitat ajuda)
    @Override
    public String toString() {
        String empleatsString = (empleats == null || empleats.isEmpty()) 
                ? "Sense empleats" 
                : String.join(", ", empleats.stream().map(Empleat::getNom).toList());

        return String.format("Departament{id=%d, nom='%s', empleats=[%s]}", id, nom, empleatsString);
    }
}