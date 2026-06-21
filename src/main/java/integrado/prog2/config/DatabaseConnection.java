package integrado.prog2.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static Connection getConnection() {
        try {
            String url = dotenv.get("DB_URL", "jdbc:mysql://localhost:3306/pedidos_db");
            String user = dotenv.get("DB_USER", "root");
            String password = dotenv.get("DB_PASSWORD", "");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error conectando a la base de datos", e);
        }
    }
}