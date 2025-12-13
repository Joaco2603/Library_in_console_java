package cr.ac.ucenfotec.dl.dao;

import cr.ac.ucenfotec.bl.entities.User;
import cr.ac.ucenfotec.bl.entities.Role;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación de UserDAO usando MySQL.
 * 
 * @author Joaquín
 * @version 1.0
 */
public class UserDAOMySQL implements UserDAO {
    private ArrayList<User> users;
    private Connection connection;

    public UserDAOMySQL() {
        this.users = new ArrayList<>();
        this.connection = DatabaseConnection.getInstance().getConnection();
        load();
    }

    @Override
    public void addUser(User u) {
        if (u == null) {
            System.err.println("Error: User no puede ser null");
            return;
        }

        String sql = "INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, u.getId());
            pstmt.setString(2, u.getFirstName());
            pstmt.setString(3, u.getLastName());
            pstmt.setString(4, u.getEmail());
            pstmt.setString(5, u.getPassword());
            pstmt.setInt(6, u.getRole().getId());

            pstmt.executeUpdate();
            users.add(u);
            System.out.println("✓ Usuario guardado: " + u.getFullName());
        } catch (SQLException e) {
            System.err.println("✗ Error guardando usuario: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public void load() {
        users.clear();
        String sql = "SELECT u.*, r.role_name, r.description FROM users u " +
                 "LEFT JOIN roles r ON u.role_id = r.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Role role = new Role(
                    rs.getInt("role_id"),
                    rs.getString("role_name"),
                    rs.getString("description")
                );

                User user = new User(
                    rs.getString("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    role
                );
                users.add(user);
            }
            System.out.println("✓ Cargados " + users.size() + " usuarios desde MySQL");
        } catch (SQLException e) {
            System.err.println("✗ Error cargando usuarios: " + e.getMessage());
        }
    }

    @Override
    public void save() {
        // En MySQL no necesitamos guardar explícitamente toda la lista
        System.out.println("ℹ Usando MySQL - persistencia automática");
    }

    /**
     * Busca un usuario por ID.
     */
    public User buscarPorId(String id) {
        String sql = "SELECT u.*, r.role_name, r.description FROM users u " +
                 "LEFT JOIN roles r ON u.role_id = r.id " +
                     "WHERE u.id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Role role = new Role(
                    rs.getInt("role_id"),
                    rs.getString("role_name"),
                    rs.getString("description")
                );

                return new User(
                    rs.getString("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    role
                );
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando usuario por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un usuario por email.
     */
    public User buscarPorEmail(String email) {
        String sql = "SELECT u.*, r.role_name, r.description FROM users u " +
                 "LEFT JOIN roles r ON u.role_id = r.id " +
                     "WHERE u.email = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Role role = new Role(
                    rs.getInt("role_id"),
                    rs.getString("role_name"),
                    rs.getString("description")
                );

                return new User(
                    rs.getString("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    role
                );
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando usuario por email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Verifica las credenciales de un usuario.
     */
    public User autenticar(String email, String password) {
        User user = buscarPorEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Busca usuarios por rol.
     */
    public ArrayList<User> buscarPorRol(String roleName) {
        ArrayList<User> resultados = new ArrayList<>();
        String sql = "SELECT u.*, r.role_name, r.description FROM users u " +
                 "LEFT JOIN roles r ON u.role_id = r.id " +
                     "WHERE r.role_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, roleName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Role role = new Role(
                    rs.getInt("role_id"),
                    rs.getString("role_name"),
                    rs.getString("description")
                );

                resultados.add(new User(
                    rs.getString("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    role
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando por rol: " + e.getMessage());
        }
        return resultados;
    }

    /**
     * Actualiza un usuario existente.
     */
    public boolean actualizar(User user) {
        if (user == null) {
            System.err.println("Error: User a actualizar no puede ser null");
            return false;
        }

        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, role_id = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getRole().getId());
            pstmt.setString(6, user.getId());

            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Actualizar también en la lista en memoria
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId().equals(user.getId())) {
                        users.set(i, user);
                        break;
                    }
                }
                System.out.println("✓ Usuario actualizado: " + user.getFullName());
                return true;
            } else {
                System.err.println("✗ Usuario no encontrado para actualizar: ID " + user.getId());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error actualizando usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un usuario por ID.
     */
    public boolean eliminar(String id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                users.removeIf(user -> user.getId().equals(id));
                System.out.println("✓ Usuario eliminado: ID " + id);
                return true;
            } else {
                System.err.println("✗ Usuario no encontrado para eliminar: ID " + id);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error eliminando usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si existe un usuario con el email dado.
     */
    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }

    /**
     * Cuenta usuarios por rol.
     */
    public int contarPorRol(String roleName) {
        String sql = "SELECT COUNT(*) FROM users u " +
                     "LEFT JOIN roles r ON u.role_id = r.id " +
                     "WHERE r.role_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, roleName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error contando usuarios por rol: " + e.getMessage());
        }
        return 0;
    }
}
