CREATE TABLE Departament (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE Empleat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    departament_id INT NOT NULL,
    FOREIGN KEY (departament_id) REFERENCES Departament(id) ON DELETE CASCADE
);

CREATE TABLE Tasca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcio VARCHAR(255) NOT NULL
);

CREATE TABLE Empleat_Tasca (
    empleat_id INT NOT NULL,
    tasca_id INT NOT NULL,
    PRIMARY KEY (empleat_id, tasca_id),
    FOREIGN KEY (empleat_id) REFERENCES Empleat(id) ON DELETE CASCADE,
    FOREIGN KEY (tasca_id) REFERENCES Tasca(id) ON DELETE CASCADE
);

CREATE TABLE Historic (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tasca_id INT NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comentari TEXT,
    FOREIGN KEY (tasca_id) REFERENCES Tasca(id) ON DELETE CASCADE
);