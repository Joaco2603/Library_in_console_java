package cr.ac.ucenfotec.bl.entities;

public class ReserveEntity {
    public ReserveEntity(String reserveDate, String status, BookEntity book, UserEntity user) {
        this.reserveDate = reserveDate;
        this.status = status;
        this.book = book;
        this.user = user;
    }
    private int id;
    private String reserveDate;
    private String status;
    private BookEntity book;
    private UserEntity user;

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

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
