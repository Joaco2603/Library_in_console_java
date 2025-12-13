package cr.ac.ucenfotec.dl.jdbc;

import cr.ac.ucenfotec.dl.dao.UserDAO;
import cr.ac.ucenfotec.bl.entities.User;
import cr.ac.ucenfotec.bl.entities.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class UsersDAOJdbc implements UserDAO {
    private final String table = "users";

    @Override
    public void addUser(User u) {
        String sql = "INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getId() == null ? UUID.randomUUID().toString() : u.getId());
            ps.setString(2, u.getFirstName());
            ps.setString(3, u.getLastName());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPassword());
            ps.setObject(6, u.getRole() != null ? u.getRole().getId() : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding user via JDBC: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT u.id,u.first_name,u.last_name,u.email,u.password,r.id as role_id,r.role_name,r.description FROM users u LEFT JOIN roles r ON u.role_id = r.id";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                String roleDesc = rs.getString("description");
                Role role = null;
                if (roleName != null) role = new Role(roleId, roleName, roleDesc);
                list.add(new User(id, first, last, email, pass, role));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users via JDBC: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void load() {
        // no-op for JDBC (getUsers always queries DB)
    }

    @Override
    public void save() {
        // no-op for JDBC
    }
}
