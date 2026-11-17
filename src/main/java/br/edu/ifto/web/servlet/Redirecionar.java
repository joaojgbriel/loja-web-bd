package br.edu.ifto.web.servlet;

import javax.servlet.http.HttpServletResponse;

/**
 * Faz o redirect (302) mantendo o endereço RELATIVO no cabeçalho Location.
 *
 * O response.sendRedirect() do servidor transforma o caminho em endereço completo
 * (ex.: http://localhost:8080/). Atrás de um proxy, como o do GitHub Codespaces,
 * o servidor só enxerga "localhost", e o navegador era mandado para um endereço errado.
 * Com o caminho relativo ("/"), o próprio navegador completa com o endereço que ele está usando.
 */
final class Redirecionar {

    private Redirecionar() {
    }

    static void para(HttpServletResponse response, String caminho) {
        response.setStatus(HttpServletResponse.SC_FOUND); // 302
        response.setHeader("Location", caminho);
    }
}
