package com.iticbcn.danimerida.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Historic")
public class Historic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String comentari;

    @ManyToOne
    @JoinColumn(name = "tasca_id", nullable = false)
    private Tasca tasca;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    public Tasca getTasca() {
        return tasca;
    }

    public void setTasca(Tasca tasca) {
        this.tasca = tasca;
    }

    // toString per modificar la sortida per pantalla de forma controlada
    @Override
    public String toString() {
        String tascaStr = (tasca != null) ? tasca.getDescripcio() : "Sense Tasca";
        return String.format("Historic{id=%d, comentari='%s', tasca='%s'}", id, comentari, tascaStr);
    }
}