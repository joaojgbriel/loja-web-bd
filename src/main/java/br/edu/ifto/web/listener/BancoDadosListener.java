package br.edu.ifto.web.listener;

import br.edu.ifto.web.dao.ConnectionFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Prepara o banco de dados quando a aplicação inicia.
 *
 * 1. Executa o schema.sql (cria as tabelas, se ainda não existirem).
 * 2. Se a tabela de produtos estiver vazia, executa o data.sql (dados iniciais).
 *
 * Assim o projeto roda em qualquer máquina sem precisar criar o banco à mão.
 */
@WebListener
public class BancoDadosListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            executarScript(conn, "schema.sql");

            if (tabelaVazia(conn, "produto")) {
                executarScript(conn, "data.sql");
                event.getServletContext().log("Banco criado e populado com os dados iniciais.");
            }
        } catch (SQLException | IOException e) {
            throw new IllegalStateException("Não foi possível preparar o banco de dados.", e);
        }
    }

    private boolean tabelaVazia(Connection conn, String tabela) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tabela)) {
            rs.next();
            return rs.getInt(1) == 0;
        }
    }

    /** Lê um arquivo .sql de src/main/resources e executa cada comando separado por ";". */
    private void executarScript(Connection conn, String arquivo) throws IOException, SQLException {
        String conteudo;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(arquivo)) {
            if (in == null) {
                throw new IOException("Arquivo " + arquivo + " não encontrado.");
            }
            conteudo = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }

        // Remove as linhas de comentário (que começam com --).
        StringBuilder semComentarios = new StringBuilder();
        for (String linha : conteudo.split("\\R")) {
            if (!linha.trim().startsWith("--")) {
                semComentarios.append(linha).append('\n');
            }
        }

        try (Statement stmt = conn.createStatement()) {
            for (String comando : semComentarios.toString().split(";")) {
                if (!comando.isBlank()) {
                    stmt.execute(comando.trim());
                }
            }
        }
    }
}
