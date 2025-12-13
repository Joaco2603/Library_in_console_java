package cr.ac.ucenfotec.dl.jdbc;

import cr.ac.ucenfotec.dl.dao.ReserveDAO;
import cr.ac.ucenfotec.bl.entities.Reserve;
import cr.ac.ucenfotec.bl.entities.Book;
import cr.ac.ucenfotec.bl.entities.User;

import java.sql.*;
import java.util.ArrayList;

public class ReservesDAOJdbc implements ReserveDAO {
    @Override
    public void addReserve(Reserve r) {
        String sql = "INSERT INTO reserves (reserve_date,status,book_id,user_id) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, r.getReserveDate() != null ? Date.valueOf(r.getReserveDate()) : null);
            ps.setString(2, r.getStatus());
            ps.setObject(3, r.getBook() != null ? r.getBook().getId() : null);
            ps.setString(4, r.getUser() != null ? r.getUser().getId() : null);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) r.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error adding reserve via JDBC: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Reserve> getReserves() {
        ArrayList<Reserve> list = new ArrayList<>();
        String sql = "SELECT r.id,r.reserve_date,r.status,b.id as book_id,b.title,b.author,b.isbn,b.year,b.available,u.id as user_id,u.first_name,u.last_name,u.email,u.password,ro.id as role_id,ro.role_name,ro.description FROM reserves r LEFT JOIN books b ON r.book_id = b.id LEFT JOIN users u ON r.user_id = u.id LEFT JOIN roles ro ON u.role_id = ro.id";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Date d = rs.getDate("reserve_date");
                String reserveDate = d != null ? d.toString() : null;
                String status = rs.getString("status");

                Book book = null;
                int bookId = rs.getInt("book_id");
                if (!rs.wasNull()) {
                    book = new Book(bookId, rs.getString("title"), rs.getString("author"), rs.getString("isbn"), rs.getInt("year"), rs.getBoolean("available"));
                }

                User user = null;
                String userId = rs.getString("user_id");
                if (userId != null) {
                    int roleId = rs.getInt("role_id");
                    String roleName = rs.getString("role_name");
                    String roleDesc = rs.getString("description");
                    cr.ac.ucenfotec.bl.entities.Role role = null;
                    if (roleName != null) role = new cr.ac.ucenfotec.bl.entities.Role(roleId, roleName, roleDesc);
                    user = new User(userId, rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), role);
                }

                Reserve r = new Reserve(reserveDate, status, book, user);
                r.setId(id);
                list.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching reserves via JDBC: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void load() { }

    @Override
    public void save() { }
}
