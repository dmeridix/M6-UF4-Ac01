package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.Departament;

import org.hibernate.SessionFactory;

public class DepartamentDAO extends GenDAOImpl<Departament>{
    public DepartamentDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Departament.class);
    }
}