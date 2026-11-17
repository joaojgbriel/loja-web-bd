package br.edu.ifto.web.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Produto da loja.
 *
 * Os getters são obrigatórios: a EL (${produto.nome}) chama getNome() por baixo dos panos.
 */
public class Produto implements Serializable {

    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String icone;

    public Produto(int id, String nome, String descricao, BigDecimal preco, String icone) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.icone = icone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getIcone() {
        return icone;
    }
}
