
package cr.ac.ucenfotec.bl.handlers;
import cr.ac.ucenfotec.dl.BooksData;
import cr.ac.ucenfotec.bl.entities.BookEntity;
import java.util.ArrayList;
import java.util.UUID;

public class BookHandler {
	private BooksData dataBooks;

	public BookHandler() {
		dataBooks = new BooksData();
	}

	public void addBook(String title, String author, String isbn) {
		BookEntity book = new BookEntity(UUID.randomUUID().toString(), title, author, isbn);
		dataBooks.addBook(book);
	}

	public ArrayList<BookEntity> getAllBooks() {
		return dataBooks.getBooks();
	}
}
