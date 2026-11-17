package br.edu.ifto.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Recurso: /logout
 *
 * GET /logout -> encerra a sessão do usuário
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // 1. Recupera a sessão existente (false = não cria uma nova só para destruí-la).
        HttpSession session = request.getSession(false);

        // 2. Invalida a sessão inteira: some o usuarioLogado e também o carrinho.
        //    (Diferente do "Limpar carrinho", que usa removeAttribute e mantém a sessão viva.)
        if (session != null) {
            session.invalidate();
        }

        // 3. Redireciona para a página inicial.
        response.sendRedirect(request.getContextPath() + "/");
    }
}
