package cr.ac.ucenfotec.dl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class para obtener conexiones JDBC a la base MySQL.
 * Lee parámetros por defecto compatibles con el `docker-compose.yml` incluido.
 */
public class DBConnection {
    private static final String URL = System.getenv().getOrDefault("LIB_DB_URL", "jdbc:mysql://127.0.0.1:3306/librarydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    private static final String USER = System.getenv().getOrDefault("LIB_DB_USER", "libraryuser");
    private static final String PASS = System.getenv().getOrDefault("LIB_DB_PASS", "librarypass");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Add connector to classpath.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
