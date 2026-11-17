package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoDAO;
import br.edu.ifto.web.model.ItemCarrinho;
import br.edu.ifto.web.model.Produto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Recurso: /carrinho/itens
 *
 * POST   /carrinho/itens         -> adiciona um produto (parâmetro id vem do formulário)
 * DELETE /carrinho/itens?id={id} -> remove o item (id vem na query string)
 */
@WebServlet("/carrinho/itens")
public class CarrinhoItemServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer id = CarrinhoSessao.lerId(request);
        Produto produto = (id == null) ? null : produtoDAO.buscarPorId(id);
        if (produto == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Produto inválido");
            return;
        }

        Map<Integer, ItemCarrinho> carrinho = CarrinhoSessao.obterOuCriar(request);

        ItemCarrinho item = carrinho.get(id);
        if (item == null) {
            carrinho.put(id, new ItemCarrinho(produto)); // primeira vez: quantidade 1
        } else {
            item.incrementar();                           // já existe: quantidade + 1
        }

        // Padrão POST-Redirect-GET: o navegador recebe 302 e faz um GET em /carrinho.
        // Assim, apertar F5 não reenvia o formulário.
        Redirecionar.para(response, request.getContextPath() + "/carrinho");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer id = CarrinhoSessao.lerId(request); // request.getParameter("id") da query string
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Informe ?id=");
            return;
        }

        Map<Integer, ItemCarrinho> carrinho = CarrinhoSessao.obterExistente(request);
        if (carrinho == null || carrinho.remove(id) == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Item não está no carrinho");
            return;
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
