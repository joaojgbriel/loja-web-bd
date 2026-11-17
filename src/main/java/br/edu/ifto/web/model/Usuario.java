package br.edu.ifto.web.model;

import java.io.Serializable;

/**
 * Usuário da loja.
 *
 * Os getters são obrigatórios: a EL (${usuarioLogado.nome}) chama getNome() por baixo dos panos.
 */
public class Usuario implements Serializable {

    private int id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    /** Usado só para conferir a senha no login. */
    public boolean senhaConfere(String senhaInformada) {
        return senha != null && senha.equals(senhaInformada);
    }
}
