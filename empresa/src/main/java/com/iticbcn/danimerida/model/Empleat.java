package com.iticbcn.danimerida.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "Empleat")
public class Empleat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "departament_id", nullable = false)
    private Departament departament;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Empleat_Tasca", joinColumns = @JoinColumn(name = "empleat_id"), inverseJoinColumns = @JoinColumn(name = "tasca_id"))
    private Set<Tasca> tasques;

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

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public Set<Tasca> getTasques() {
        return tasques;
    }

    public void setTasques(Set<Tasca> tasques) {
        this.tasques = tasques;
    }

    // toString per modificar la sortida per pantalla de forma controlada (en
    // aquesta part he necessitat ajuda)
    @Override
    public String toString() {
        String tasquesString = (tasques == null || tasques.isEmpty()) 
                ? "Sense tasques" 
                : String.join(", ", tasques.stream().map(Tasca::getDescripcio).toList());

        String departamentString = (departament != null) ? departament.getNom() : "Sense Departament";

        return String.format("Empleat{id=%d, nom='%s', departament='%s', tasques=[%s]}", 
                            id, nom, departamentString, tasquesString);
    }
}
