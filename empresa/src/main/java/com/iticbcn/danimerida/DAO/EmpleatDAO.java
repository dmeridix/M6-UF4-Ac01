package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class EmpleatDAO extends GenDAOImpl<Empleat>{
    private SessionFactory sessionFactory;
    public EmpleatDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Empleat.class);
    }
    // Consultes HQL 
    public List<Object[]> contarEmpleatsPerDepartament() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery(
                "SELECT e.departament.nom, COUNT(e) FROM Empleat e GROUP BY e.departament.nom",
                Object[].class
                ).list();
        }
    }
}