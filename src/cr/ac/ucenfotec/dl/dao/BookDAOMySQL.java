package cr.ac.ucenfotec.dl.dao;

import cr.ac.ucenfotec.bl.entities.Book;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación de BookDAO usando MySQL.
 * 
 * @author Joaquín
 * @version 1.0
 */
public class BookDAOMySQL implements BookDAO {
    private ArrayList<Book> books;
    private Connection connection;

    public BookDAOMySQL() {
        this.books = new ArrayList<>();
        this.connection = DatabaseConnection.getInstance().getConnection();
        load();
    }

    @Override
    public void addBook(Book b) {
        if (b == null) {
            System.err.println("Error: Book no puede ser null");
            return;
        }

        String sql = "INSERT INTO books (id, title, author, isbn, year, available) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, b.getId());
            pstmt.setString(2, b.getTitle());
            pstmt.setString(3, b.getAuthor());
            pstmt.setString(4, b.getIsbn());
            pstmt.setInt(5, b.getYear());
            pstmt.setBoolean(6, b.isAvailable());

            pstmt.executeUpdate();
            books.add(b);
            System.out.println("✓ Libro guardado: " + b.getTitle());
        } catch (SQLException e) {
            System.err.println("✗ Error guardando libro: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    public void load() {
        books.clear();
        String sql = "SELECT * FROM books";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year"),
                    rs.getBoolean("available")
                );
                books.add(book);
            }
            System.out.println("✓ Cargados " + books.size() + " libros desde MySQL");
        } catch (SQLException e) {
            System.err.println("✗ Error cargando libros: " + e.getMessage());
        }
    }

    @Override
    public void save() {
        // En MySQL no necesitamos guardar explícitamente toda la lista
        // ya que cada operación (add, update, delete) modifica la BD directamente
        System.out.println("ℹ Usando MySQL - persistencia automática");
    }

    /**
     * Busca un libro por ID.
     */
    public Book buscarPorId(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year"),
                    rs.getBoolean("available")
                );
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando libro por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un libro por ISBN.
     */
    public Book buscarPorISBN(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year"),
                    rs.getBoolean("available")
                );
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando libro por ISBN: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca libros por título (búsqueda parcial).
     */
    public ArrayList<Book> buscarPorTitulo(String titulo) {
        ArrayList<Book> resultados = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + titulo + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                resultados.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year"),
                    rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando por título: " + e.getMessage());
        }
        return resultados;
    }

    /**
     * Busca libros por autor.
     */
    public ArrayList<Book> buscarPorAutor(String autor) {
        ArrayList<Book> resultados = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + autor + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                resultados.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year"),
                    rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            System.err.println("✗ Error buscando por autor: " + e.getMessage());
        }
        return resultados;
    }

    /**
     * Actualiza un libro existente.
     */
    public boolean actualizar(Book book) {
        if (book == null) {
            System.err.println("Error: Book a actualizar no puede ser null");
            return false;
        }

        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, year = ?, available = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setInt(4, book.getYear());
            pstmt.setBoolean(5, book.isAvailable());
            pstmt.setInt(6, book.getId());

            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Actualizar también en la lista en memoria
                for (int i = 0; i < books.size(); i++) {
                    if (books.get(i).getId() == book.getId()) {
                        books.set(i, book);
                        break;
                    }
                }
                System.out.println("✓ Libro actualizado: " + book.getTitle());
                return true;
            } else {
                System.err.println("✗ Libro no encontrado para actualizar: ID " + book.getId());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error actualizando libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un libro por ID.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                books.removeIf(book -> book.getId() == id);
                System.out.println("✓ Libro eliminado: ID " + id);
                return true;
            } else {
                System.err.println("✗ Libro no encontrado para eliminar: ID " + id);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error eliminando libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cuenta los libros disponibles.
     */
    public int contarLibrosDisponibles() {
        String sql = "SELECT COUNT(*) FROM books WHERE available = true";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error contando libros disponibles: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Obtiene el próximo ID disponible.
     */
    public int obtenerProximoId() {
        String sql = "SELECT MAX(id) FROM books";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error obteniendo próximo ID: " + e.getMessage());
        }
        return 1;
    }
}
