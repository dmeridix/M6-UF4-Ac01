package com.iticbcn.danimerida.DAO;

import com.iticbcn.danimerida.model.Departament;
import com.iticbcn.danimerida.model.Empleat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class DepartamentDAO extends GenDAOImpl<Departament> {
    public DepartamentDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Departament.class);
    }

    public void deleteDepartament(int id) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();

            Departament departament = session.get(Departament.class, id);
            if (departament == null) {
                System.out.println("El departament no existeix.");
                session.getTransaction().rollback();
                return;
            }

            Departament senseDepartament = session.get(Departament.class, 0);
            if (senseDepartament == null) {
                senseDepartament = new Departament();
                senseDepartament.setId(0);
                senseDepartament.setNom("Sense Departament");
                session.persist(senseDepartament);
            }

            List<Empleat> empleats = session.createQuery(
                "FROM Empleat WHERE departament.id = :deptId", Empleat.class)
                .setParameter("deptId", id)
                .list();

            for (Empleat empleat : empleats) {
                empleat.setDepartament(senseDepartament);
                session.merge(empleat);
            }

            session.remove(departament);
            session.getTransaction().commit();
            System.out.println("Departament eliminat correctament.");
        } catch (Exception e) {
            System.err.println("Error al eliminar el departament: " + e.getMessage());
        }
    }
}
