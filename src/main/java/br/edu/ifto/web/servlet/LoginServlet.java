package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.UsuarioDAO;
import br.edu.ifto.web.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Recurso: /login
 *
 * GET  /login -> apresenta o formulário de login
 * POST /login -> processa as credenciais
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /** Nome do atributo da sessão exigido pela atividade. */
    static final String USUARIO_LOGADO = "usuarioLogado";

    private static final String PAGINA_LOGIN = "/WEB-INF/views/login.jsp";

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // forward: a JSP fica em WEB-INF, então só é acessível por aqui (GET /login), nunca por /login.jsp.
        request.getRequestDispatcher(PAGINA_LOGIN).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = usuarioDAO.autenticar(email, senha);

        if (usuario == null) {
            // INVÁLIDO: o erro vai no escopo de REQUISIÇÃO e fazemos forward.
            // É a mesma requisição, então a JSP consegue ler ${erro}.
            request.setAttribute("erro", "E-mail ou senha inválida.");
            request.getRequestDispatcher(PAGINA_LOGIN).forward(request, response);
            return;
        }

        // VÁLIDO: o usuário vai para a SESSÃO, pois precisa continuar disponível nas próximas requisições.
        // request.getSession() cria a sessão se ela ainda não existir.
        HttpSession session = request.getSession();
        session.setAttribute(USUARIO_LOGADO, usuario);

        // Padrão POST-Redirect-GET: o navegador recebe 302 e faz um GET em /.
        // Assim, apertar F5 na página inicial não reenvia o formulário de login.
        response.sendRedirect(request.getContextPath() + "/");
    }
}
