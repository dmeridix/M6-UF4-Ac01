package com.iticbcn.danimerida;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iticbcn.danimerida.DAO.DepartamentDAO;
import com.iticbcn.danimerida.DAO.EmpleatDAO;
import com.iticbcn.danimerida.DAO.HistoricDAO;
import com.iticbcn.danimerida.DAO.TascaDAO;
import com.iticbcn.danimerida.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            boolean continuarPrograma = true;
            // Bucle per controlar la execució del programa fins l'usuari vulgui
            while (continuarPrograma) {

                Object dao = seleccionarTaula(br, sessionFactory);
                if (dao == null) {
                    System.out.println("Opció no vàlida. Tornant al menú...");
                    continue;
                }

                boolean continuarConTabla = true;
                while (continuarConTabla) {
                    seleccionarAccio(dao, br);

                    System.out.print("Vols fer una altra acció sobre la mateixa taula? (s/n) >> ");
                    String respuestaAccion = br.readLine().trim().toLowerCase();
                    continuarConTabla = respuestaAccion.equals("s");
                }

                System.out.print("Vols canviar de taula o sortir del programa? (t = nova taula, s = sortir) >> ");
                String respuestaTabla = br.readLine().trim().toLowerCase();
                if (respuestaTabla.equals("s")) {
                    continuarPrograma = false;
                }
            }
            System.out.println("Programa finalitzat. Adéu!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            sessionFactory.close();
        }
    }

    // Metode que retorna un Object que equival a la instancia DAO de la clase que
    // indica l'usuari per linea de comandes
    public static Object seleccionarTaula(BufferedReader br, SessionFactory session) throws IOException {
        System.out.println("Selecciona la taula:");
        System.out.println("1. Departaments");
        System.out.println("2. Empleats");
        System.out.println("3. Tasques");
        System.out.println("4. Historic");
        System.out.print("Introdueix l'opció >> ");

        int taula = Integer.parseInt(br.readLine());

        return switch (taula) {
            case 1 -> new DepartamentDAO(session);
            case 2 -> new EmpleatDAO(session);
            case 3 -> new TascaDAO(session);
            case 4 -> new HistoricDAO(session);
            default -> null;
        };
    }

    // Metode que selecciona l'acció a realitzar sobre la taula seleccionada per linea de comandes
    public static void seleccionarAccio(Object dao, BufferedReader br) throws Exception {
        if (dao == null)
            return;

        System.out.println("Selecciona l'acció:");
        System.out.println("1. Llistar");
        System.out.println("2. Llistar per ID");
        System.out.println("3. Inserir");
        System.out.println("4. Actualitzar");
        System.out.println("5. Eliminar");

        // Consultes HQL especialitzades
        if (dao instanceof EmpleatDAO) {
            System.out.println("6. Comptar empleats per departament");
        } else if (dao instanceof TascaDAO) {
            System.out.println("6. Comptar tasques per empleat");
        }

        System.out.print("Introdueix l'opció >> ");

        int accio = Integer.parseInt(br.readLine());

        
        switch (accio) {
            // Per poder saber quina clase es i quin metode usar fem us del instanceof per distingir cada clase
            case 1:
                if (dao instanceof DepartamentDAO) {
                    ((DepartamentDAO) dao).getAll().forEach(System.out::println);
                } else if (dao instanceof EmpleatDAO) {
                    ((EmpleatDAO) dao).getAll().forEach(System.out::println);
                } else if (dao instanceof TascaDAO) {
                    ((TascaDAO) dao).getAll().forEach(System.out::println);
                } else if (dao instanceof HistoricDAO) {
                    ((HistoricDAO) dao).getAll().forEach(System.out::println);
                }
                break;
            case 2:
                System.out.print("Introdueix ID >> ");
                int id = Integer.parseInt(br.readLine());

                if (dao instanceof DepartamentDAO) {
                    System.out.println(((DepartamentDAO) dao).get(id));
                } else if (dao instanceof EmpleatDAO) {
                    System.out.println(((EmpleatDAO) dao).get(id));
                } else if (dao instanceof TascaDAO) {
                    System.out.println(((TascaDAO) dao).get(id));
                } else if (dao instanceof HistoricDAO) {
                    System.out.println(((HistoricDAO) dao).get(id));
                }
                break;
            case 3:
                insertarEntidad(dao, br);
                break;
            case 4:
                actualizarEntidad(dao, br);
                break;
            case 5:
                eliminarEntidad(dao, br);
                break;
            case 6:
                if (dao instanceof EmpleatDAO) {
                    List<Object[]> resultat = ((EmpleatDAO) dao).contarEmpleatsPerDepartament();
                    for (Object[] fila : resultat) {
                        System.out.println("Departament: " + fila[0] + " - Nombre d'empleats: " + fila[1]);
                    }
                } else if (dao instanceof TascaDAO) {
                    List<Object[]> resultat = ((TascaDAO) dao).contarTasquesPerEmpleat();
                    for (Object[] fila : resultat) {
                        System.out.println("Empleat: " + fila[0] + " - Nombre de tasques: " + fila[1]);
                    }
                }
                break;
            default:
                System.out.println("Opció no vàlida.");
        }
    }

    // Metode per poder fer tots els inserts de cada clase independentment de les sebes necessitats
    // es diferencien utilitzn el instanceof sobre Object
    public static void insertarEntidad(Object dao, BufferedReader br) throws Exception {
        if (dao instanceof DepartamentDAO) {
            System.out.print("Introdueix el nom del departament >> ");
            String nom = br.readLine();
            Departament departament = new Departament();
            departament.setNom(nom);
            ((DepartamentDAO) dao).save(departament);
        } else if (dao instanceof EmpleatDAO) {
            System.out.print("Introdueix el nom de l'empleat >> ");
            String nom = br.readLine();
            System.out.print("Introdueix l'ID del departament >> ");
            int departamentId = Integer.parseInt(br.readLine());

            Departament departament = new Departament();
            departament.setId(departamentId);

            System.out.print("Introdueix els IDs de les tasques assignades (separats per comes) >> ");
            String tascaIdsStr = br.readLine();
            Set<Tasca> tasques = new HashSet<>();

            for (String tascaId : tascaIdsStr.split(",")) {
                Tasca tasca = new Tasca();
                tasca.setId(Integer.parseInt(tascaId.trim()));
                tasques.add(tasca);
            }

            Empleat empleat = new Empleat();
            empleat.setNom(nom);
            empleat.setDepartament(departament);
            empleat.setTasques(tasques);

            ((EmpleatDAO) dao).save(empleat);
        } else if (dao instanceof TascaDAO) {
            System.out.print("Introdueix la descripció de la tasca >> ");
            String descripcio = br.readLine();

            System.out.print("Introdueix els IDs dels empleats assignats (separats per comes) >> ");
            String empleatIdsStr = br.readLine();
            Set<Empleat> empleats = new HashSet<>();

            for (String empleatId : empleatIdsStr.split(",")) {
                Empleat empleat = new Empleat();
                empleat.setId(Integer.parseInt(empleatId.trim()));
                empleats.add(empleat);
            }

            Tasca tasca = new Tasca();
            tasca.setDescripcio(descripcio);
            tasca.setEmpleats(empleats);

            ((TascaDAO) dao).save(tasca);
        } else if (dao instanceof HistoricDAO) {
            System.out.print("Introdueix el comentari >> ");
            String comentari = br.readLine();
            System.out.print("Introdueix l'ID de la tasca >> ");
            int tascaId = Integer.parseInt(br.readLine());

            Tasca tasca = new Tasca();
            tasca.setId(tascaId);

            Historic historic = new Historic();
            historic.setComentari(comentari);
            historic.setTasca(tasca);

            ((HistoricDAO) dao).save(historic);
        } else {
            System.out.println("DAO no reconegut.");
        }
    }

    // Metode per poder actulaitzar els registres de cada clase independentment de les sebes necessitats
    // es diferencien utilitzn el instanceof sobre Object
    public static void actualizarEntidad(Object dao, BufferedReader br) throws Exception {
        System.out.print("Introdueix ID de l'entitat a actualitzar >> ");
        int id = Integer.parseInt(br.readLine());

        if (dao instanceof DepartamentDAO) {
            Departament departament = ((DepartamentDAO) dao).get(id);
            if (departament != null) {
                System.out.print("Introdueix el nou nom del departament >> ");
                String nom = br.readLine();
                departament.setNom(nom);
                ((DepartamentDAO) dao).update(departament);
            }
        } else if (dao instanceof EmpleatDAO) {
            Empleat empleat = ((EmpleatDAO) dao).get(id);
            if (empleat != null) {
                System.out.print("Introdueix el nou nom de l'empleat >> ");
                String nom = br.readLine();
                empleat.setNom(nom);
                System.out.print("Introdueix el nou ID del departament >> ");
                int departamentId = Integer.parseInt(br.readLine());
                Departament departament = new Departament();
                departament.setId(departamentId);
                empleat.setDepartament(departament);
                ((EmpleatDAO) dao).update(empleat);
            }
        } else if (dao instanceof TascaDAO) {
            Tasca tasca = ((TascaDAO) dao).get(id);
            if (tasca != null) {
                System.out.print("Introdueix la nova descripció de la tasca >> ");
                String descripcio = br.readLine();
                tasca.setDescripcio(descripcio);
                ((TascaDAO) dao).update(tasca);
            }
        } else if (dao instanceof HistoricDAO) {
            Historic historic = ((HistoricDAO) dao).get(id);
            if (historic != null) {
                System.out.print("Introdueix el nou comentari >> ");
                String comentari = br.readLine();
                historic.setComentari(comentari);
                ((HistoricDAO) dao).update(historic);
            }
        } else {
            System.out.println("DAO no reconegut.");
        }
    }

    // Metode per poder eliminar els registres de cada clase independentment de les sebes necessitats
    // es diferencien utilitzn el instanceof sobre Object
    public static void eliminarEntidad(Object dao, BufferedReader br) throws Exception {
        System.out.print("Introdueix ID de l'entitat a eliminar >> ");
        int id = Integer.parseInt(br.readLine());

        if (dao instanceof DepartamentDAO) {
            ((DepartamentDAO) dao).deleteDepartament(id);
        } else if (dao instanceof EmpleatDAO) {
            Empleat empleat = ((EmpleatDAO) dao).get(id);
            if (empleat != null) {
                ((EmpleatDAO) dao).delete(empleat);
            }
        } else if (dao instanceof TascaDAO) {
            Tasca tasca = ((TascaDAO) dao).get(id);
            if (tasca != null) {
                ((TascaDAO) dao).delete(tasca);
            }
        } else if (dao instanceof HistoricDAO) {
            Historic historic = ((HistoricDAO) dao).get(id);
            if (historic != null) {
                ((HistoricDAO) dao).delete(historic);
            }
        } else {
            System.out.println("DAO no reconegut.");
        }
    }

}
