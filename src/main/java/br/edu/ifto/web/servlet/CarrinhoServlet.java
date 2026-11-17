package br.edu.ifto.web.servlet;

import br.edu.ifto.web.model.ItemCarrinho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Recurso: /carrinho
 *
 * GET    /carrinho -> mostra o carrinho da sessão atual
 * DELETE /carrinho -> limpa o carrinho da sessão
 */
@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<Integer, ItemCarrinho> carrinho = CarrinhoSessao.obterExistente(request);

        List<ItemCarrinho> itens = new ArrayList<>();
        if (carrinho != null) {
            itens.addAll(carrinho.values());
        }

        request.setAttribute("itens", itens);
        request.setAttribute("quantidadeTotal", CarrinhoSessao.quantidadeTotal(carrinho));
        request.setAttribute("valorTotal", CarrinhoSessao.valorTotal(carrinho));

        request.getRequestDispatcher("/WEB-INF/views/carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            // Remove SÓ o atributo "carrinho". Não usamos session.invalidate(),
            // que apagaria a sessão inteira (e qualquer outro atributo dela).
            session.removeAttribute(CarrinhoSessao.ATRIBUTO);
        }

        // 204 No Content: deu certo e não há corpo na resposta.
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
