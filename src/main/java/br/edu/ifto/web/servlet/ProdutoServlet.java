package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Recurso: /produtos
 *
 * GET /produtos -> lista os produtos
 */
@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Escopo de REQUISIÇÃO: os dados só precisam existir até a JSP ser montada.
        request.setAttribute("produtos", produtoDAO.listarTodos());
        request.setAttribute("quantidadeTotal",
                CarrinhoSessao.quantidadeTotal(CarrinhoSessao.obterExistente(request)));

        // forward: o servidor repassa a mesma requisição para a JSP (a URL no navegador não muda).
        request.getRequestDispatcher("/WEB-INF/views/produtos.jsp").forward(request, response);
    }
}
