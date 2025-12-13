
package cr.ac.ucenfotec.bl.handlers;

import cr.ac.ucenfotec.bl.entities.Book;
import cr.ac.ucenfotec.dl.dao.BookDAO;
import cr.ac.ucenfotec.dl.dao.BookDAOMySQL;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler que expone operaciones de negocio relacionadas con libros.
 * Actúa como capa intermedia entre la UI y el DAO (`BooksData`).
 */
public class BookHandler {
    private final BookDAO data;
    private int nextId;

    public BookHandler() {
        this(new BookDAOMySQL());
    }

    public BookHandler(BookDAO dao) {
        this.data = dao;
        this.nextId = calculateNextId();

        // if no books exist, add some defaults
        if (data.getBooks().isEmpty()) {
            addBook("Cien años de soledad", "Gabriel García Márquez", "978-0307474728", 1967);
            addBook("Don Quijote de la Mancha", "Miguel de Cervantes", "978-8424936464", 1605);
            addBook("1984", "George Orwell", "978-0451524935", 1949);
        }
    }

    private int calculateNextId() {
        int max = 0;
        for (Book b : data.getBooks()) {
            if (b.getId() > max) max = b.getId();
        }
        return max + 1;
    }

    /** Crea y persiste un libro con los datos completos. */
    public Book addBook(String title, String author, String isbn, int year) {
        Book book = new Book(nextId++, title, author, isbn, year, true);
        data.addBook(book);
        return book;
    }

    // Sobrecarga: permitir agregar libro con solo título y autor (valores por defecto)
    public Book addBook(String title, String author) {
        return addBook(title, author, "N/A", 0);
    }

    /** Elimina un libro por ISBN y persiste la eliminación si procede. */
    public boolean deleteBookByIsbn(String isbn) {
        boolean removed = data.getBooks().removeIf(book -> book.getIsbn().equals(isbn));
        if (removed) data.save();
        return removed;
    }

    /** Retorna todos los libros (copia defensiva). */
    public List<Book> getAllBooks() {
        return new ArrayList<>(data.getBooks());
    }

    /** Busca un libro por su ISBN. */
    public Book findBookByIsbn(String isbn) {
        return data.getBooks().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    /** Busca libros por texto (título, autor o isbn). */
    public List<Book> searchBooks(String query) {
        String lowerQuery = query.toLowerCase();
        return data.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerQuery) ||
                               book.getAuthor().toLowerCase().contains(lowerQuery) ||
                               book.getIsbn().contains(lowerQuery))
                .toList();
    }

    /** Retorna los libros disponibles actualmente. */
    public List<Book> getAvailableBooks() {
        return data.getBooks().stream()
                .filter(Book::isAvailable)
                .toList();
    }

    /** Persiste los datos manejados por este handler. */
    public void save() {
        data.save();
    }
}
