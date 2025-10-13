
package cr.ac.ucenfotec.bl.handlers;
import cr.ac.ucenfotec.dl.BooksData;
import cr.ac.ucenfotec.bl.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookHandler {
    private final List<Book> books;
    private int nextId;

    public BookHandler() {
        this.books = new ArrayList<>();
        this.nextId = 1;
        initializeBooks();
    }

    private void initializeBooks() {
        addBook("Cien años de soledad", "Gabriel García Márquez", "978-0307474728", 1967);
        addBook("Don Quijote de la Mancha", "Miguel de Cervantes", "978-8424936464", 1605);
        addBook("1984", "George Orwell", "978-0451524935", 1949);
    }

    public Book addBook(String title, String author, String isbn, int year) {
        Book book = new Book(nextId++, title, author, isbn, year, true);
        books.add(book);
        return book;
    }

    public boolean deleteBookByIsbn(String isbn) {
        return books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> searchBooks(String query) {
        String lowerQuery = query.toLowerCase();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerQuery) ||
                               book.getAuthor().toLowerCase().contains(lowerQuery) ||
                               book.getIsbn().contains(lowerQuery))
                .toList();
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .toList();
    }
}
