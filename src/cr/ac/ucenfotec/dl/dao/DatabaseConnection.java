package cr.ac.ucenfotec.dl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton para manejar la conexión a MySQL.
 * 
 * @author Joaquín
 * @version 1.0
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("DB_PORT", "3306");
    private static final String DB   = System.getenv().getOrDefault("DB_NAME", "librarydb");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "libraryuser");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASS", "librarypass");
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✓ Conexión MySQL: " + URL + " (user=" + USER + ")");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Driver MySQL no encontrado: " + e.getMessage());
            System.err.println("  Asegúrate de tener mysql-connector-java en el classpath");
        } catch (SQLException e) {
            System.err.println("✗ Error conectando a MySQL: " + e.getMessage());
            System.err.println("  Verifica Docker: docker compose up -d y credenciales en docker-compose.yml");
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error verificando/reconectando: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("✗ Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}
