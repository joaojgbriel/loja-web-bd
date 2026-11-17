package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de produtos: substitui o antigo ProdutoMemoriaRepository.
 *
 * Antes os produtos ficavam numa lista em memória; agora são lidos da tabela "produto".
 * Os métodos têm os mesmos nomes de antes, então os Servlets quase não mudaram.
 *
 * Os CARRINHOS continuam fora do banco: cada carrinho vive na HttpSession do seu usuário.
 */
public class ProdutoDAO {

    public List<Produto> listarTodos() {
        String sql = "SELECT id, nome, descricao, preco, icone FROM produto ORDER BY id";
        List<Produto> produtos = new ArrayList<>();

        // try-with-resources: conexão, statement e resultado são fechados automaticamente.
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(montarProduto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos.", e);
        }
        return produtos;
    }

    /** Retorna o produto com esse id, ou null se não existir. */
    public Produto buscarPorId(int id) {
        String sql = "SELECT id, nome, descricao, preco, icone FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // PreparedStatement com "?" evita SQL Injection: o valor é enviado separado do comando.
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarProduto(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto " + id + ".", e);
        }
        return null;
    }

    /** Converte a linha atual do ResultSet em um objeto Produto. */
    private Produto montarProduto(ResultSet rs) throws SQLException {
        return new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getBigDecimal("preco"),
                rs.getString("icone"));
    }
}
