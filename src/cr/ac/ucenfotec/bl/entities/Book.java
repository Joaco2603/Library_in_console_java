package cr.ac.ucenfotec.bl.entities;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int year;
    private boolean available;

    public Book(int id, String title, String author, String isbn, int year, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Year: " + year + ", Available: " + available;
    }
}
