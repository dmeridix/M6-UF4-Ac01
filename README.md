## Preguntes de reflexió i sintesi 

### 1. De quina lògica d’aplicació s’encarrega el Patró DAO?  
El patró DAO s'encarrega de la gestió de dades entre l'aplicació i la base de dades. Proporciona una capa d'abstracció que encapsula les operacions de persistència, evitant que la lògica de negoci interactuï directament amb Hibernate.  

### 2️. Per què considereu que és útil el patró DAO i en què us ha servit?  
El patró DAO és útil perquè facilita la mantenibilitat i escalabilitat del codi, ja que separa la lògica d'accés a dades de la resta del sistema. 

### 3. Heu hagut de fer cap ajust al vostre codi d’aplicació?  
Sí, he fet els següents canvis per ajustar el meu codi d'aplicació:  

- **Gestió d'errors millorada** per evitar problemes quan s'eliminen departaments amb empleats assignats. Ara, en eliminar un departament, els seus empleats passen a tenir `departament = "Sense departament"`.  
- **Correcció del problema amb `sessionFactory` nul** en la funcionalitat de comptar empleats per departament i tasques per empleat.  

### 4. Diagrama de Classes Actualitzat  

```mermaid
classDiagram
    class Employee {
        +id: int
        +name: String
        +surname: String
        +email: String
        +phone: String
        +departmentId: int
    }

    class EmployeeDAO {
        +getEmployeeById(id: int): Employee
        +getAllEmployees(): List~Employee~
        +insertEmployee(e: Employee): void
        +updateEmployee(e: Employee): void
        +deleteEmployee(id: int): void
    }

    class Department {
        +id: int
        +name: String
        +location: String
    }

    class DepartmentDAO {
        +getDepartmentById(id: int): Department
        +getAllDepartments(): List~Department~
        +insertDepartment(d: Department): void
        +updateDepartment(d: Department): void
        +deleteDepartment(id: int): void
    }

    class Task {
        +id: int
        +description: String
        +status: String
        +employeeId: int
    }

    class TaskDAO {
        +getTaskById(id: int): Task
        +getAllTasks(): List~Task~
        +insertTask(t: Task): void
        +updateTask(t: Task): void
        +deleteTask(id: int): void
    }

    class AbstractDAO {
        +sessionFactory: SessionFactory
    }

    class IGenericDAO {
        <<interface>>
        +getById(id: int)
        +getAll()
        +insert(obj)
        +update(obj)
        +delete(id: int)
    }

    %% Inheritance
    AbstractDAO <|-- EmployeeDAO : "Extends"
    AbstractDAO <|-- DepartmentDAO : "Extends"
    AbstractDAO <|-- TaskDAO : "Extends"

    %% Interface Implementation
    IGenericDAO <|.. EmployeeDAO : "Implements"
    IGenericDAO <|.. DepartmentDAO : "Implements"
    IGenericDAO <|.. TaskDAO : "Implements"

    %% Class Usage Relationships
    EmployeeDAO --> Employee : "Uses"
    DepartmentDAO --> Department : "Uses"
    TaskDAO --> Task : "Uses"

    %% Entity Relationships
    Employee "1" --> "1" Department : "Belongs to"
    Department "1" --> "*" Employee : "Has"

    Employee "1" --> "*" Task : "Assigned to"
    Task "*" --> "1" Employee : "Performed by"
````
### 5. Valoració de la Classe Abstracta  
La classe abstracta evita la repetició de codi definint operacions comunes per diverses entitats. En la UF2 A02, amb JDBC, hauria permès reutilitzar mètodes per gestionar consultes SQL de manera més eficient. 
