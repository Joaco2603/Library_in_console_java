Library_in_console_java — MySQL Docker setup and quick start

Este repositorio contiene la aplicación de consola Java para gestionar una biblioteca.

Archivos añadidos para usar MySQL con Docker:
- `db/init.sql` — script SQL que crea la base `librarydb`, tablas (`roles`, `users`, `books`, `reserves`) y datos de ejemplo.
- `docker-compose.yml` — levanta un contenedor MySQL 8 y ejecuta `init.sql` automáticamente.

Instrucciones (Windows PowerShell):

1) Asegúrate de tener Docker y Docker Compose instalados.

2) Levantar la base de datos:

```powershell
cd C:\Programming\cenfotec\portafolio_poo\Library_in_console_java
docker-compose up -d
```

3) Ver logs del contenedor (opcional):

```powershell
docker-compose logs -f db
```

4) Conectarse a la base de datos desde la terminal (si tienes cliente mysql):

```powershell
mysql -h 127.0.0.1 -P 3306 -u libraryuser -p
# password: librarypass
USE librarydb;
SHOW TABLES;
SELECT * FROM users;
```

5) Cadena JDBC para conectar desde Java:

```
jdbc:mysql://localhost:3306/librarydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

Notas:
- Las DAOs actuales en el proyecto persisten en archivos `data/*.txt`. Si migras a MySQL, adapta las implementaciones DAO (`UsersData`, `BooksData`, `ReservesData`) para usar JDBC o un ORM.
- Las credenciales por defecto del contenedor son:
  - root: `rootpassword`
  - libraryuser: `librarypass` (base `librarydb`)

JDBC (uso con las nuevas implementaciones DAO)
- Este repositorio ahora incluye implementaciones JDBC de los DAOs en `src/cr/ac/ucenfotec/dl/jdbc/`.
- Para compilar y ejecutar la app con JDBC necesitas añadir el conector MySQL (`mysql-connector-java`) al classpath.

Ejemplo (descargar jar y ejecutar con PowerShell):

```powershell
# compilar (desde la raíz del proyecto) - ajusta rutas si hace falta:
javac -cp .;mysql-connector-java-8.0.34.jar -d out $(Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object FullName)

# ejecutar Main:
java -cp out;mysql-connector-java-8.0.34.jar Main
```

También puedes exportar el .jar y usar `-cp` al ejecutar.

Detener y eliminar contenedores y volúmenes:

```powershell
docker-compose down -v
```
# Biblioteca en Consola (Java)

Un sencillo sistema de gestión de biblioteca basado en consola escrito en Java. Esta aplicación permite a los usuarios registrarse, iniciar sesión y gestionar operaciones de biblioteca como reservar, devolver y buscar libros, además de ver usuarios.

## Funcionalidades
- Registro e inicio de sesión de usuarios.
- Reservar libros.
- Devolver libros.
- Buscar libros.
- Ver usuarios.
- Navegación mediante menú en consola.

## Estructura de Carpetas
```
src/
  Main.java
  cr/
    ac/
      ucenfotec/
        bl/
          entities/
            BookEntity.java
            LibrarianEntity.java
            ReserveEntity.java
            UserEntity.java
          handlers/
            BookHandler.java
            LibrarianHandler.java
            ReserveHandler.java
            UserHandler.java
        dl/
          DataBooks.java
          DataLibrarians.java
          DataReserves.java
          DataUsers.java
        tl/
          Controller.java
        ui/
          UI.java
```

## Cómo Ejecutar
1. Compilar el proyecto:
   ```
   javac src/cr/ac/ucenfotec/ui/UI.java src/Main.java
   ```
2. Ejecutar la clase principal:
   ```
   java -cp src Main
   ```

## Requisitos
- Java 8 o superior

## Autor
Joaquin Alberto Pappa Larreal

## Licencia
Este proyecto está licenciado bajo la Licencia MIT.
