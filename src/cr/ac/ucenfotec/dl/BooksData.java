package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.BookEntity;
import java.util.ArrayList;

public class BooksData {
    private ArrayList<BookEntity> books;

    public BooksData() {
        books = new ArrayList<>();
    }

    public void addBook(BookEntity b) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(b);
    }

    public ArrayList<BookEntity> getBooks() {
        return books;
    }
}
