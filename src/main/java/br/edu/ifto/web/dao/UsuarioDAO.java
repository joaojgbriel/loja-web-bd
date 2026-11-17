package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO de usuários: substitui o antigo UsuarioMemoriaRepository.
 *
 * Antes os usuários ficavam numa lista em memória; agora são lidos da tabela "usuario".
 * O usuário LOGADO continua na HttpSession (atributo "usuarioLogado").
 */
public class UsuarioDAO {

    /** Retorna o usuário com esse e-mail, ou null se não existir. */
    public Usuario buscarPorEmail(String email) {
        if (email == null) {
            return null;
        }

        String sql = "SELECT id, nome, email, senha FROM usuario WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // O e-mail digitado vai como parâmetro, nunca concatenado no SQL (evita SQL Injection).
            stmt.setString(1, email.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário.", e);
        }
        return null;
    }

    /** Retorna o usuário se e-mail e senha conferirem, ou null caso contrário. */
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null && usuario.senhaConfere(senha)) {
            return usuario;
        }
        return null;
    }
}
