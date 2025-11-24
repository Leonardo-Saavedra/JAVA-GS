package br.com.fiap.corpcare.model;

import br.com.fiap.corpcare.model.enums.CategoriaAtividade;

public class AtividadeRecomendada {

    private Integer idAtividade;
    private InteracaoChatbot interacaoChatbot;
    private String nome;
    private CategoriaAtividade categoria;
    private String descricao;

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    public InteracaoChatbot getInteracaoChatbot() {
        return interacaoChatbot;
    }

    public void setInteracaoChatbot(InteracaoChatbot interacaoChatbot) {
        this.interacaoChatbot = interacaoChatbot;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaAtividade getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAtividade categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
