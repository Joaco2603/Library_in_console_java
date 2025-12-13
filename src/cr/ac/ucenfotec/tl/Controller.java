package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.ui.UI;
import cr.ac.ucenfotec.bl.handlers.RoleHandler;
import cr.ac.ucenfotec.bl.handlers.UserHandler;
import cr.ac.ucenfotec.bl.handlers.BookHandler;
import cr.ac.ucenfotec.bl.handlers.ReserveHandler;
import cr.ac.ucenfotec.bl.entities.User;
import cr.ac.ucenfotec.bl.entities.Book;
import cr.ac.ucenfotec.bl.entities.Reserve;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;

public class Controller {
    // Instancia de la clase UI para manejar la interfaz de usuario
    private final UI UIInterface = new UI();
    private final Scanner scanner = new Scanner(System.in);
    // Handler para manejar usuarios
    private final UserHandler userHandler = new UserHandler();
    // Handler para manejar libros
    private final BookHandler bookHandler = new BookHandler();
    // Handler para manejar reservas
    private final ReserveHandler reserveHandler = new ReserveHandler();
    // Simula si el usuario esta logeado o no
    private boolean isLogged = false;
    // Usuario actualmente logeado
    private User currentUser = null;

    public Controller() {
        // Registrar shutdown hook para asegurarnos de persistir datos en salida inesperada
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                saveAll();
            } catch (Exception e) {
                System.err.println("Error during shutdown save: " + e.getMessage());
            }
        }));
    }

    public void start() throws IOException {
        int option = -1;

        do {
            UIInterface.displayAccess();
            option = UIInterface.readOption();
            processAccess(option);
        } while (!isLogged);

        do {
            if (userHandler.isAdmin(currentUser)) {
                UIInterface.displayAdminMenu();
                option = UIInterface.readOption();
                processAdminOption(option);
            } else {
                UIInterface.displayUserMenu();
                option = UIInterface.readOption();
                processUserOption(option);
            }
        } while (option != 5);

        // Guardar todos los datos antes de salir normalmente
        saveAll();
    }

    /** Persiste los datos manejados por los distintos handlers. */
    private void saveAll() {
        try {
            userHandler.save();
        } catch (Exception ignored) {}
        try {
            bookHandler.save();
        } catch (Exception ignored) {}
        try {
            reserveHandler.save();
        } catch (Exception ignored) {}
    }

    public void processAccess(int option) throws IOException {
        switch (option) {
            case 1:
                processLogin();
                break;
            case 2:
                processRegister();
                break;
            default:
                displayError();
                break;
        }
    }

    public void processLogin() {
        // Solicitar credenciales al usuario
        String[] credentials = getLoginCredentials();
        String email = credentials[0];
        String password = credentials[1];

        // Buscar usuario en el sistema
        User user = userHandler.findUserByEmailAndPassword(email, password);

        if (user != null) {
            this.currentUser = user;
            isLogged = true;
            displayLoginSuccess(user.getFullName());
        } else {
            displayLoginFailure();
            isLogged = false;
        }
    }

    public void processRegister() {
        // Solicitar datos de registro al usuario
        String[] registerData = getRegisterData();
        String first_name = registerData[0];
        String last_name = registerData[1];
        String password = registerData[2];
        String email = registerData[3];

        RoleHandler roleHandler = new RoleHandler();

        // Agregar el usuario al sistema
        this.currentUser = userHandler.addUser(first_name, last_name, email, password, roleHandler.getDefaultRoleId());

        displayRegisterSuccess();
        isLogged = true;
    }

    public void processLogout() {
        isLogged = false;
    }

    public void processAdminOption(int option) throws IOException {
        do {
            UIInterface.displayAdminMenu();
            option = UIInterface.readOption();
            switch (option) {
                case 1:
                    processAddBook();
                    break;
                case 2:
                    processDeleteBook();
                    break;
                case 3:
                    displayAllBooks();
                    break;
                case 4:
                    displayUsers();
                    break;
                case 5:
                    displayExit();
                    break;
                default:
                    displayError();
                    break;
            }
        } while (option != 5);
    }

    public void processUserOption(int option) throws IOException {
        do {
            UIInterface.displayUserMenu();
            option = UIInterface.readOption();
            switch (option) {
                case 1:
                    processBookReservation(option);
                    break;
                case 2:
                    processBookReturn(option);
                    break;
                case 3:
                    processBookSearch(option);
                    break;
                case 4:
                    displayUsers();
                    break;
                case 5:
                    displayExit();
                    break;
                default:
                    displayError();
                    break;
            }
        } while (option != 5);
    }

    public void processBookReservation(int option) {
        List<Book> availableBooks = bookHandler.getAvailableBooks();

        if (availableBooks.isEmpty()) {
            displayBookReservationNoAvailableBooks();
            return;
        }

        // Pass string representations to UI (Controller avoids using entity internals)
        List<String> availableBookStrings = availableBooks.stream().map(Book::toString).toList();
        displayAvailableBooks(availableBookStrings);
        String isbn = getBookIsbn();
        Book book = bookHandler.findBookByIsbn(isbn);

        if (book == null) {
            displayBookReservationBookNotFound(isbn);
            return;
        }

        if (!book.isAvailable()) {
            displayBookReservationNotAvailable(book.toString());
            return;
        }

        Reserve reserve = reserveHandler.addReserve(LocalDate.now().toString(), "ACTIVE", book, currentUser);
        book.setAvailable(false);
        displayBookReservationSuccess(reserve.toString());
    }

    public void processBookReturn(int option) {
        List<Reserve> activeReserves = reserveHandler.getActiveReservesByUser(currentUser);

        if (activeReserves.isEmpty()) {
            displayBookReturnNoReservations();
            return;
        }

        List<String> reserveStrings = activeReserves.stream().map(Reserve::toString).toList();
        displayUserActiveReserves(reserveStrings);
        String isbn = getBookIsbn();
        Book book = bookHandler.findBookByIsbn(isbn);

        if (book == null) {
            displayBookReturnBookNotFound(isbn);
            return;
        }

        Reserve activeReserve = reserveHandler.findActiveReserveByBookAndUser(book, currentUser);

        if (activeReserve == null) {
            displayBookReturnNoActiveReservation(book.toString());
            return;
        }

        book.setAvailable(true);
        reserveHandler.updateReserveStatus(activeReserve, "RETURNED");
        displayBookReturnSuccess(book.toString());
    }

    public void processBookSearch(int option) {
        String query = getBookSearchQuery();

        if (query == null || query.trim().isEmpty()) {
            displayBookSearchInvalidQuery();
            return;
        }

        List<Book> results = bookHandler.searchBooks(query.trim());

        if (results.isEmpty()) {
            displayBookSearchNoResults(query);
        } else {
            List<String> resultStrings = results.stream().map(Book::toString).toList();
            displayBookSearchResults(query, resultStrings);
        }
    }

    public void displayUsers() {
        userHandler.getAllUsers().forEach(user -> displayUserInfo(user.toString()));
    }

    private void processAddBook() {
        String[] bookData = getBookData();
        String title = bookData[0];
        String author = bookData[1];
        String isbn = bookData[2];
        int year = Integer.parseInt(bookData[3]);
        
        bookHandler.addBook(title, author, isbn, year);
        displayBookAddSuccess();
    }

    private void processDeleteBook() {
        String isbn = getBookIsbn();
        boolean deleted = bookHandler.deleteBookByIsbn(isbn);
        
        if (deleted) {
            displayBookDeleteSuccess();
        } else {
            displayBookDeleteFailure();
        }
    }

    private void displayAllBooks() {
        bookHandler.getAllBooks().forEach(book -> displayBookInfo(book.toString()));
    }

    // ===== Métodos de UI movidos al Controller =====
    
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
