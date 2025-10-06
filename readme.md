# Library in Console (Java)

A simple console-based library management system written in Java. This application allows users to register, log in, and manage library operations such as reserving, returning, and searching for books, as well as viewing users.

## Features
- User registration and login
- Reserve books
- Return books
- Search for books
- View users
- Console-based menu navigation

## Folder Structure
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

## How to Run
1. Compile the project:
   ```
   javac src/cr/ac/ucenfotec/ui/UI.java src/Main.java
   ```
2. Run the main class:
   ```
   java -cp src Main
   ```

## Requirements
- Java 8 or higher

## Author
Joaco2603

## License
This project is licensed under the MIT License.
