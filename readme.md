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
