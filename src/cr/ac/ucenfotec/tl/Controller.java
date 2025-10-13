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

import java.io.IOException;

public class Controller {
    // Instancia de la clase UI para manejar la interfaz de usuario
    private final UI UIInterface = new UI();
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

    public void start() throws IOException {
        int option = -1;

        do {
            UIInterface.displayAccess();
            option = UIInterface.readOption();
            processAccess(option);
        } while (!isLogged);

        do {
            if(currentUser.getRole().getRoleName().equals("admin")){
                UIInterface.displayAdminMenu();
                option = UIInterface.readOption();
                processAdminOption(option);
            } else {
                UIInterface.displayUserMenu();
                option = UIInterface.readOption();
                processUserOption(option);
            }
        } while (option != 5);
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
                UIInterface.displayError();
                break;
        }
    }

    public void processLogin() {
        // Solicitar credenciales al usuario
        String[] credentials = UIInterface.getLoginCredentials();
        String email = credentials[0];
        String password = credentials[1];

        // Buscar usuario en el sistema
        User user = userHandler.findUserByEmailAndPassword(email, password);

        if (user != null) {
            this.currentUser = user;
            isLogged = true;
            UIInterface.displayLoginSuccess(user.getFullName());
        } else {
            UIInterface.displayLoginFailure();
            isLogged = false;
        }
    }

    public void processRegister() {
        // Solicitar datos de registro al usuario
        String[] registerData = UIInterface.getRegisterData();
        String first_name = registerData[0];
        String last_name = registerData[1];
        String password = registerData[2];
        String email = registerData[3];

        RoleHandler roleHandler = new RoleHandler();

        // Agregar el usuario al sistema
        this.currentUser = userHandler.addUser(first_name, last_name, email, password, roleHandler.getDefaultRoleId());

        UIInterface.displayRegisterSuccess();
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
                    UIInterface.displayExit();
                    break;
                default:
                    UIInterface.displayError();
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
                    UIInterface.displayExit();
                    break;
                default:
                    UIInterface.displayError();
                    break;
            }
        } while (option != 5);
    }

    public void processBookReservation(int option) {
        List<Book> availableBooks = bookHandler.getAvailableBooks();

        if (availableBooks.isEmpty()) {
            UIInterface.displayBookReservationNoAvailableBooks();
            return;
        }

        UIInterface.displayAvailableBooks(availableBooks);
        String isbn = UIInterface.getBookIsbn();
        Book book = bookHandler.findBookByIsbn(isbn);

        if (book == null) {
            UIInterface.displayBookReservationBookNotFound(isbn);
            return;
        }

        if (!book.isAvailable()) {
            UIInterface.displayBookReservationNotAvailable(book);
            return;
        }

        Reserve reserve = reserveHandler.addReserve(LocalDate.now().toString(), "ACTIVE", book, currentUser);
        book.setAvailable(false);
        UIInterface.displayBookReservationSuccess(reserve);
    }

    public void processBookReturn(int option) {
        List<Reserve> activeReserves = reserveHandler.getActiveReservesByUser(currentUser);

        if (activeReserves.isEmpty()) {
            UIInterface.displayBookReturnNoReservations();
            return;
        }

        UIInterface.displayUserActiveReserves(activeReserves);
        String isbn = UIInterface.getBookIsbn();
        Book book = bookHandler.findBookByIsbn(isbn);

        if (book == null) {
            UIInterface.displayBookReturnBookNotFound(isbn);
            return;
        }

        Reserve activeReserve = reserveHandler.findActiveReserveByBookAndUser(book, currentUser);

        if (activeReserve == null) {
            UIInterface.displayBookReturnNoActiveReservation(book);
            return;
        }

        book.setAvailable(true);
        reserveHandler.updateReserveStatus(activeReserve, "RETURNED");
        UIInterface.displayBookReturnSuccess(book);
    }

    public void processBookSearch(int option) {
        String query = UIInterface.getBookSearchQuery();

        if (query == null || query.trim().isEmpty()) {
            UIInterface.displayBookSearchInvalidQuery();
            return;
        }

        List<Book> results = bookHandler.searchBooks(query.trim());

        if (results.isEmpty()) {
            UIInterface.displayBookSearchNoResults(query);
        } else {
            UIInterface.displayBookSearchResults(query, results);
        }
    }

    public void displayUsers() {
        userHandler.getAllUsers().forEach(user -> UIInterface.displayUserInfo(user));
    }

    private void processAddBook() {
        String[] bookData = UIInterface.getBookData();
        String title = bookData[0];
        String author = bookData[1];
        String isbn = bookData[2];
        int year = Integer.parseInt(bookData[3]);
        
        bookHandler.addBook(title, author, isbn, year);
        UIInterface.displayBookAddSuccess();
    }

    private void processDeleteBook() {
        String isbn = UIInterface.getBookIsbn();
        boolean deleted = bookHandler.deleteBookByIsbn(isbn);
        
        if (deleted) {
            UIInterface.displayBookDeleteSuccess();
        } else {
            UIInterface.displayBookDeleteFailure();
        }
    }

    private void displayAllBooks() {
        bookHandler.getAllBooks().forEach(book -> UIInterface.displayBookInfo(book));
    }
}
