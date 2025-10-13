package cr.ac.ucenfotec.bl.entities;

public class Reserve {
    private int id;
    private String reserveDate;
    private String status;
    private Book book;
    private User user;

    public Reserve(String reserveDate, String status, Book book, User user) {
        this.reserveDate = reserveDate;
        this.status = status;
        this.book = book;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "ID: " + id + ", Reserve Date: " + reserveDate + ", Status: " + status + ", Book: [" + book.toString()
                + "], User: [" + user.toString() + "]";
    }
}
