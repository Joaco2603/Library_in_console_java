package cr.ac.ucenfotec.dl.dao;

import cr.ac.ucenfotec.bl.entities.Reserve;
import cr.ac.ucenfotec.bl.entities.Book;
import cr.ac.ucenfotec.bl.entities.User;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación de ReserveDAO usando MySQL.
 * 
 * @author Joaquín
 * @version 1.0
 */
public class ReserveDAOMySQL implements ReserveDAO {
    private ArrayList<Reserve> reserves;
    private Connection connection;
    private BookDAOMySQL bookDAO;
    private UserDAOMySQL userDAO;

    public ReserveDAOMySQL(BookDAOMySQL bookDAO, UserDAOMySQL userDAO) {
        this.reserves = new ArrayList<>();
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        load();
    }

    @Override
    public void addReserve(Reserve r) {
        if (r == null) {
            System.err.println("Error: Reserve no puede ser null");
            return;
        }

        String sql = "INSERT INTO reserves (reserve_date, status, book_id, user_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, r.getReserveDate());
            pstmt.setString(2, r.getStatus());
            pstmt.setInt(3, r.getBook().getId());
            pstmt.setString(4, r.getUser().getId());

            pstmt.executeUpdate();

            // Obtener el ID generado automáticamente
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                r.setId(generatedKeys.getInt(1));
            }

            reserves.add(r);
            System.out.println("✓ Reserva guardada: " + r.getBook().getTitle() + " para " + r.getUser().getFullName());
        } catch (SQLException e) {
            System.err.println("✗ Error guardando reserva: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Reserve> getReserves() {
        return reserves;
    }

    @Override
    public void load() {
        reserves.clear();
        String sql = "SELECT * FROM reserves";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String userId = rs.getString("user_id");

                Book book = bookDAO.buscarPorId(bookId);
                User user = userDAO.buscarPorId(userId);

                if (book != null && user != null) {
                    Reserve reserve = new Reserve(
                        rs.getString("reserve_date"),
                        rs.getString("status"),
                        book,
                        user
                    );
                    reserve.setId(rs.getInt("id"));
                    reserves.add(reserve);
                }
            }
            System.out.println("✓ Cargadas " + reserves.size() + " reservas desde MySQL");
        } catch (SQLException e) {
            System.err.println("✗ Error cargando reservas: " + e.getMessage());
        }
    }

    @Override
    public void save() {
        // En MySQL no necesitamos guardar explícitamente toda la lista
        System.out.println("ℹ Usando MySQL - persistencia automática");
    }

    /**
     * Busca una reserva por ID.
     */
    public Reserve buscarPorId(int id) {
        String sql = "SELECT * FROM reserves WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                String userId = rs.getString("user_id");

                Book book = bookDAO.buscarPorId(bookId);
                User user = userDAO.buscarPorId(userId);

                if (book != null && user != null) {
                    Reserve reserve = new Reserve(
                        rs.getString("reserve_date"),
                        rs.getString("status"),
                        book,
                        user
                    );
                    reserve.setId(rs.getInt("id"));
                    return reserve;
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando reserva por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca reservas por usuario.
     */
    public ArrayList<Reserve> buscarPorUsuario(String userId) {
        ArrayList<Reserve> resultados = new ArrayList<>();
        String sql = "SELECT * FROM reserves WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("book_id");

                Book book = bookDAO.buscarPorId(bookId);
                User user = userDAO.buscarPorId(userId);

                if (book != null && user != null) {
                    Reserve reserve = new Reserve(
                        rs.getString("reserve_date"),
                        rs.getString("status"),
                        book,
                        user
                    );
                    reserve.setId(rs.getInt("id"));
                    resultados.add(reserve);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando reservas por usuario: " + e.getMessage());
        }
        return resultados;
    }

    /**
     * Busca reservas por libro.
     */
    public ArrayList<Reserve> buscarPorLibro(int bookId) {
        ArrayList<Reserve> resultados = new ArrayList<>();
        String sql = "SELECT * FROM reserves WHERE book_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("user_id");

                Book book = bookDAO.buscarPorId(bookId);
                User user = userDAO.buscarPorId(userId);

                if (book != null && user != null) {
                    Reserve reserve = new Reserve(
                        rs.getString("reserve_date"),
                        rs.getString("status"),
                        book,
                        user
                    );
                    reserve.setId(rs.getInt("id"));
                    resultados.add(reserve);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando reservas por libro: " + e.getMessage());
        }
        return resultados;
    }

    /**
     * Busca reservas por estado.
     */
    public ArrayList<Reserve> buscarPorEstado(String status) {
        ArrayList<Reserve> resultados = new ArrayList<>();
        String sql = "SELECT * FROM reserves WHERE status = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String userId = rs.getString("user_id");

                Book book = bookDAO.buscarPorId(bookId);
                User user = userDAO.buscarPorId(userId);

                if (book != null && user != null) {
                    Reserve reserve = new Reserve(
                        rs.getString("reserve_date"),
                        rs.getString("status"),
                        book,
                        user
                    );
                    reserve.setId(rs.getInt("id"));
                    resultados.add(reserve);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando reservas por estado: " + e.getMessage());
        }
        return resultados;
    }

    /**
     * Actualiza una reserva existente.
     */
    public boolean actualizar(Reserve reserve) {
        if (reserve == null) {
            System.err.println("Error: Reserve a actualizar no puede ser null");
            return false;
        }

        String sql = "UPDATE reserves SET reserve_date = ?, status = ?, book_id = ?, user_id = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, reserve.getReserveDate());
            pstmt.setString(2, reserve.getStatus());
            pstmt.setInt(3, reserve.getBook().getId());
            pstmt.setString(4, reserve.getUser().getId());
            pstmt.setInt(5, reserve.getId());

            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Actualizar también en la lista en memoria
                for (int i = 0; i < reserves.size(); i++) {
                    if (reserves.get(i).getId() == reserve.getId()) {
                        reserves.set(i, reserve);
                        break;
                    }
                }
                System.out.println("✓ Reserva actualizada: ID " + reserve.getId());
                return true;
            } else {
                System.err.println("✗ Reserva no encontrada para actualizar: ID " + reserve.getId());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error actualizando reserva: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una reserva por ID.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM reserves WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                reserves.removeIf(reserve -> reserve.getId() == id);
                System.out.println("✓ Reserva eliminada: ID " + id);
                return true;
            } else {
                System.err.println("✗ Reserva no encontrada para eliminar: ID " + id);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error eliminando reserva: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cuenta reservas activas.
     */
    public int contarReservasActivas() {
        String sql = "SELECT COUNT(*) FROM reserves WHERE status = 'active'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error contando reservas activas: " + e.getMessage());
        }
        return 0;
    }
}
