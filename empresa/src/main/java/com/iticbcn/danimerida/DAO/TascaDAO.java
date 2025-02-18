package com.iticbcn.danimerida.DAO;


import com.iticbcn.danimerida.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class TascaDAO  extends GenDAOImpl<Tasca>{
    private SessionFactory sessionFactory;
    public TascaDAO(SessionFactory sessionFactory) {
        super(sessionFactory,Tasca.class);
    }
        // Consultes HQL
    public List<Object[]> contarTasquesPerEmpleat() {
        try (Session sessio = sessionFactory.openSession()) {
            return sessio.createQuery(
                "SELECT e.nom, COUNT(t) FROM Empleat e JOIN e.tasques t GROUP BY e.nom",
                Object[].class
            ).list();
        }
    }
    
}
