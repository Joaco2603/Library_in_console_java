package cr.ac.ucenfotec.dl.jdbc;

import cr.ac.ucenfotec.dl.dao.BookDAO;
import cr.ac.ucenfotec.bl.entities.Book;

import java.sql.*;
import java.util.ArrayList;

public class BooksDAOJdbc implements BookDAO {
    @Override
    public void addBook(Book b) {
        String sql = "INSERT INTO books (id,title,author,isbn,year,available) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, b.getId());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getAuthor());
            ps.setString(4, b.getIsbn());
            ps.setInt(5, b.getYear());
            ps.setBoolean(6, b.isAvailable());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding book via JDBC: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Book> getBooks() {
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT id,title,author,isbn,year,available FROM books";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");
                int year = rs.getInt("year");
                boolean available = rs.getBoolean("available");
                list.add(new Book(id, title, author, isbn, year, available));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching books via JDBC: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void load() { }

    @Override
    public void save() { }
}
