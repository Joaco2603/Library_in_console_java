package cr.ac.ucenfotec.ui;

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

    public void displayBookInfo(String bookInfo) {
        System.out.println("\n--- Libro ---");
        System.out.println(bookInfo);
        System.out.println("-------------");
    }

    public void displayUserInfo(String userInfo) {
        System.out.println("\n--- Usuario ---");
        System.out.println(userInfo);
        System.out.println("---------------");
    }

    public void displayAvailableBooks(List<String> books) {
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

    public void displayBookReservationNotAvailable(String bookInfo) {
        System.out.println("\n✗ El libro ya está reservado o prestado: " + bookInfo);
    }

    public void displayBookReservationSuccess(String reserveInfo) {
        System.out.println("\n✓ Reserva registrada correctamente.");
        System.out.println(reserveInfo);
    }

    public void displayUserActiveReserves(List<String> reserves) {
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

    public void displayBookReturnNoActiveReservation(String bookInfo) {
        System.out.println("\n✗ No tienes una reserva activa para el libro: " + bookInfo);
    }

    public void displayBookReturnSuccess(String bookInfo) {
        System.out.println("\n✓ Devolución registrada correctamente.");
        displayBookInfo(bookInfo);
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

    public void displayBookSearchResults(String query, List<String> books) {
        System.out.println("\n=== Resultados de Búsqueda para '" + query + "' ===");
        if (books.isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }
        books.forEach(this::displayBookInfo);
    }

    private void displayReserveInfo(String reserveInfo) {
        System.out.println("\n--- Reserva ---");
        System.out.println(reserveInfo);
        System.out.println("----------------");
    }

    public void displayExit() {
        System.out.println("\n¡Hasta pronto!");
    }

    public void displayError() {
        System.out.println("\n✗ Opción inválida. Intente nuevamente.");
    }
}
