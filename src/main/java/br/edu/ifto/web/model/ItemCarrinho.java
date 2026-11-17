package br.edu.ifto.web.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Um produto dentro do carrinho, com a sua quantidade.
 */
public class ItemCarrinho implements Serializable {

    private final Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto) {
        this.produto = produto;
        this.quantidade = 1;
    }

    /** Usado quando o mesmo produto é adicionado de novo. */
    public void incrementar() {
        this.quantidade++;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /** Subtotal = preço × quantidade. Na JSP: ${item.subtotal} */
    public BigDecimal getSubtotal() {
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
}
