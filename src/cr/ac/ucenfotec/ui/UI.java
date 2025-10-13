package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.entities.User;
import cr.ac.ucenfotec.bl.entities.Book;
import cr.ac.ucenfotec.bl.entities.Reserve;

import java.util.List;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayAccess() {
        System.out.println("\n=== Sistema de Biblioteca ===");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.print("Seleccione una opción: ");
    }

    public void displayAdminMenu() {
        System.out.println("\n=== Menú Administrador ===");
        System.out.println("1. Agregar libro");
        System.out.println("2. Eliminar libro");
        System.out.println("3. Ver todos los libros");
        System.out.println("4. Ver usuarios");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void displayUserMenu() {
        System.out.println("\n=== Menú Usuario ===");
        System.out.println("1. Reservar libro");
        System.out.println("2. Devolver libro");
        System.out.println("3. Buscar libro");
        System.out.println("4. Ver usuarios");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public int readOption() {
        try {
            int option = Integer.parseInt(scanner.nextLine());
            return option;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String[] getLoginCredentials() {
        System.out.println("\n=== Iniciar Sesión ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        return new String[]{email, password};
    }

    public String[] getRegisterData() {
        System.out.println("\n=== Registro ===");
        System.out.print("Nombre de usuario: ");
        String name = scanner.nextLine();
        System.out.print("Apellido: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        return new String[]{name, lastName, password, email};
    }

    public String[] getBookData() {
        System.out.println("\n=== Agregar Libro ===");
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Año de publicación: ");
        String year = scanner.nextLine();
        return new String[]{title, author, isbn, year};
    }

    public String getBookIsbn() {
        System.out.print("\nIngrese el ISBN del libro: ");
        return scanner.nextLine();
    }

    public void displayLoginSuccess(String username) {
        System.out.println("\n✓ Bienvenido " + username + "!");
    }

    public void displayLoginFailure() {
        System.out.println("\n✗ Credenciales incorrectas");
    }

    public void displayRegisterSuccess() {
        System.out.println("\n✓ Usuario registrado exitosamente");
    }

    public void displayBookAddSuccess() {
        System.out.println("\n✓ Libro agregado exitosamente");
    }

    public void displayBookDeleteSuccess() {
        System.out.println("\n✓ Libro eliminado exitosamente");
    }

    public void displayBookDeleteFailure() {
        System.out.println("\n✗ No se encontró el libro con el ISBN proporcionado");
    }

    public void displayBookInfo(Book book) {
        System.out.println("\n--- Libro ---");
        System.out.println("ID: " + book.getId());
        System.out.println("Título: " + book.getTitle());
        System.out.println("Autor: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Año: " + book.getYear());
        System.out.println("Estado: " + (book.isAvailable() ? "Disponible" : "Prestado"));
        System.out.println("-------------");
    }

    public void displayUserInfo(User user) {
        System.out.println("\n--- Usuario ---");
        System.out.println("ID: " + user.getId());
        System.out.println("Nombre: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Rol: " + user.getRole().getRoleName());
        System.out.println("---------------");
    }

    public void displayAvailableBooks(List<Book> books) {
        System.out.println("\n=== Libros Disponibles ===");
        if (books.isEmpty()) {
            System.out.println("No hay libros disponibles en este momento.");
            return;
        }
        books.forEach(this::displayBookInfo);
    }

    public void displayBookReservationNoAvailableBooks() {
        System.out.println("\n✗ No hay libros disponibles para reservar en este momento.");
    }

    public void displayBookReservationBookNotFound(String isbn) {
        System.out.println("\n✗ No se encontró un libro con el ISBN " + isbn + ".");
    }

    public void displayBookReservationNotAvailable(Book book) {
        System.out.println("\n✗ El libro '" + book.getTitle() + "' ya está reservado o prestado.");
    }

    public void displayBookReservationSuccess(Reserve reserve) {
        System.out.println("\n✓ Reserva registrada correctamente.");
        System.out.println("Fecha de reserva: " + reserve.getReserveDate());
        displayBookInfo(reserve.getBook());
    }

    public void displayUserActiveReserves(List<Reserve> reserves) {
        System.out.println("\n=== Reservas Activas ===");
        if (reserves.isEmpty()) {
            System.out.println("No tienes reservas activas.");
            return;
        }
        reserves.forEach(this::displayReserveInfo);
    }

    public void displayBookReturnNoReservations() {
        System.out.println("\n✗ No tienes reservas activas para devolver.");
    }

    public void displayBookReturnBookNotFound(String isbn) {
        System.out.println("\n✗ No se encontró un libro con el ISBN " + isbn + ".");
    }

    public void displayBookReturnNoActiveReservation(Book book) {
        System.out.println("\n✗ No tienes una reserva activa para el libro '" + book.getTitle() + "'.");
    }

    public void displayBookReturnSuccess(Book book) {
        System.out.println("\n✓ Devolución registrada correctamente.");
        displayBookInfo(book);
    }

    public String getBookSearchQuery() {
        System.out.println("\n=== Buscar Libro ===");
        System.out.print("Ingrese título, autor o ISBN: ");
        return scanner.nextLine();
    }

    public void displayBookSearchInvalidQuery() {
        System.out.println("\n✗ La consulta de búsqueda no puede estar vacía.");
    }

    public void displayBookSearchNoResults(String query) {
        System.out.println("\n✗ No se encontraron libros que coincidan con '" + query + "'.");
    }

    public void displayBookSearchResults(String query, List<Book> books) {
        System.out.println("\n=== Resultados de Búsqueda para '" + query + "' ===");
        if (books.isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }
        books.forEach(this::displayBookInfo);
    }

    private void displayReserveInfo(Reserve reserve) {
        System.out.println("\n--- Reserva ---");
        System.out.println("Libro: " + reserve.getBook().getTitle() + " (ISBN: " + reserve.getBook().getIsbn() + ")");
        System.out.println("Fecha: " + reserve.getReserveDate());
        System.out.println("Estado: " + reserve.getStatus());
        System.out.println("----------------");
    }

    public void displayExit() {
        System.out.println("\n¡Hasta pronto!");
    }

    public void displayError() {
        System.out.println("\n✗ Opción inválida. Intente nuevamente.");
    }
}
