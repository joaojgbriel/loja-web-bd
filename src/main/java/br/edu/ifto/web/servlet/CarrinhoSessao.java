package br.edu.ifto.web.servlet;

import br.edu.ifto.web.model.ItemCarrinho;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Funções auxiliares para ler/criar o carrinho guardado na HttpSession.
 *
 * Atenção: os métodos são static, mas NÃO existe nenhuma VARIÁVEL static guardando carrinhos.
 * O carrinho sempre é lido de session.getAttribute("carrinho") — um por usuário.
 */
final class CarrinhoSessao {

    /** Nome do atributo exigido pela atividade. */
    static final String ATRIBUTO = "carrinho";

    private CarrinhoSessao() {
    }

    /**
     * Devolve o carrinho da sessão, criando sessão e carrinho se ainda não existirem.
     * Usado quando vamos ALTERAR o carrinho (POST).
     */
    @SuppressWarnings("unchecked")
    static Map<Integer, ItemCarrinho> obterOuCriar(HttpServletRequest request) {
        HttpSession session = request.getSession(); // cria a sessão se não existir
        Map<Integer, ItemCarrinho> carrinho = (Map<Integer, ItemCarrinho>) session.getAttribute(ATRIBUTO);
        if (carrinho == null) {
            carrinho = new LinkedHashMap<>(); // LinkedHashMap mantém a ordem de inclusão
            session.setAttribute(ATRIBUTO, carrinho);
        }
        return carrinho;
    }

    /**
     * Devolve o carrinho da sessão SEM criar sessão nova (null se não houver).
     * Usado quando só vamos LER ou REMOVER.
     */
    @SuppressWarnings("unchecked")
    static Map<Integer, ItemCarrinho> obterExistente(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false = não cria
        if (session == null) {
            return null;
        }
        return (Map<Integer, ItemCarrinho>) session.getAttribute(ATRIBUTO);
    }

    /** Soma das quantidades (ex.: 2 mouses + 1 teclado = 3). */
    static int quantidadeTotal(Map<Integer, ItemCarrinho> carrinho) {
        int total = 0;
        if (carrinho != null) {
            for (ItemCarrinho item : carrinho.values()) {
                total += item.getQuantidade();
            }
        }
        return total;
    }

    /** Soma dos subtotais. */
    static BigDecimal valorTotal(Map<Integer, ItemCarrinho> carrinho) {
        BigDecimal total = BigDecimal.ZERO;
        if (carrinho != null) {
            for (ItemCarrinho item : carrinho.values()) {
                total = total.add(item.getSubtotal());
            }
        }
        return total;
    }

    /** Converte o parâmetro "id" para int; devolve null se faltar ou for inválido. */
    static Integer lerId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id == null) {
            return null;
        }
        try {
            return Integer.valueOf(id.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
