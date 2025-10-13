package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Book;
import java.util.ArrayList;

public class BooksData {
    private ArrayList<Book> books;

    public BooksData() {
        books = new ArrayList<>();
    }

    public void addBook(Book b) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(b);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
