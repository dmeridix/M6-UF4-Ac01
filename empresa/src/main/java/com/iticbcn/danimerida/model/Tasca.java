package com.iticbcn.danimerida.model;

import java.util.Set;

public class Tasca {
    private int id;
    private String descripcio;
    private Set<Empleat> empleats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Set<Empleat> getEmpleats() {
        return empleats;
    }

    public void setEmpleats(Set<Empleat> empleats) {
        this.empleats = empleats;
    }

    // toString per modificar la sortida per pantalla de forma controlada (en
    // aquesta part he necessitat ajuda)
    @Override
    public String toString() {
        String empleatsString = (empleats == null || empleats.isEmpty())
                ? "Sense empleats"
                : String.join(", ", empleats.stream().map(Empleat::getNom).toList());

        return String.format("Tasca{id=%d, descripcio='%s', empleats=[%s]}", id, descripcio, empleatsString);
    }

}