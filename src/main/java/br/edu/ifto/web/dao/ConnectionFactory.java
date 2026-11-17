package br.edu.ifto.web.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Fábrica de conexões JDBC.
 *
 * Lê os dados de acesso do arquivo src/main/resources/db.properties
 * e entrega uma conexão nova a cada chamada de getConnection().
 * Quem pede a conexão é responsável por fechá-la (usamos try-with-resources nos DAOs).
 */
public final class ConnectionFactory {

    private static final Properties CONFIG = carregarConfiguracao();

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                CONFIG.getProperty("db.url"),
                CONFIG.getProperty("db.usuario"),
                CONFIG.getProperty("db.senha", ""));
    }

    private static Properties carregarConfiguracao() {
        Properties props = new Properties();
        try (InputStream in = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("Arquivo db.properties não encontrado.");
            }
            props.load(in);

            // Registra o driver. Em aplicações Web isso é necessário porque o
            // DriverManager nem sempre enxerga sozinho os drivers que estão no WEB-INF/lib.
            Class.forName(props.getProperty("db.driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Erro ao configurar o banco de dados.", e);
        }
        return props;
    }
}
