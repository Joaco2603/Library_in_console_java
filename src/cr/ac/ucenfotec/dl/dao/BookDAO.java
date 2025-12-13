package cr.ac.ucenfotec.dl.dao;

import cr.ac.ucenfotec.bl.entities.Book;
import java.util.ArrayList;

/**
 * Interfaz DAO para operaciones de persistencia de libros.
 */
public interface BookDAO {
    /** Agrega un libro y persiste cambios. */
    void addBook(Book b);

    /** Retorna lista de libros en memoria. */
    ArrayList<Book> getBooks();

    /** Carga libros desde almacenamiento. */
    void load();

    /** Persiste libros al almacenamiento. */
    void save();
}
