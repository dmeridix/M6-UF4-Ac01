INSERT INTO Departament (nom) VALUES ('IT'), ('RH'), ('Finances');

INSERT INTO Empleat (nom, departament_id) VALUES ('Anna Perez', 1), ('Joan Martí', 2), ('Laura Soler', 3);

INSERT INTO Tasca (descripcio) VALUES ('Desenvolupar aplicació'), ('Fer informes'), ('Gestionar contractes');

INSERT INTO Empleat_Tasca (empleat_id, tasca_id) VALUES (1,1), (2,2), (3,3);

INSERT INTO Historic (tasca_id, comentari) VALUES (1, 'Primera versió feta'), (2, 'Informe revisat');