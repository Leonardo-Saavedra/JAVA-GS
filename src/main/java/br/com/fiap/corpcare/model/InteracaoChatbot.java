package br.com.fiap.corpcare.model;

import br.com.fiap.corpcare.model.enums.Sentimento;

import java.time.LocalDateTime;

public class InteracaoChatbot {

    private Integer idIntChatbot;
    private Colaborador colaborador;
    private LocalDateTime dataInteracao;
    private String mensagem;
    private Sentimento sentimento;
    private Integer nivelEstresse;

    public Integer getIdIntChatbot() {
        return idIntChatbot;
    }

    public void setIdIntChatbot(Integer idIntChatbot) {
        this.idIntChatbot = idIntChatbot;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public LocalDateTime getDataInteracao() {
        return dataInteracao;
    }

    public void setDataInteracao(LocalDateTime dataInteracao) {
        this.dataInteracao = dataInteracao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Sentimento getSentimento() {
        return sentimento;
    }

    public void setSentimento(Sentimento sentimento) {
        this.sentimento = sentimento;
    }

    public Integer getNivelEstresse() {
        return nivelEstresse;
    }

    public void setNivelEstresse(Integer nivelEstresse) {
        this.nivelEstresse = nivelEstresse;
    }
}
