package br.com.fiap.corpcare.model;

import br.com.fiap.corpcare.model.enums.StatusAlerta;

import java.time.LocalDate;

public class AlertaSaude {

    private Integer idAlerta;
    private InteracaoChatbot interacaoChatbot;
    private AtividadeRecomendada atividadeRecomendada;
    private String tipoAlerta;
    private String descricao;
    private LocalDate dataAlerta;
    private StatusAlerta status;

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public InteracaoChatbot getInteracaoChatbot() {
        return interacaoChatbot;
    }

    public void setInteracaoChatbot(InteracaoChatbot interacaoChatbot) {
        this.interacaoChatbot = interacaoChatbot;
    }

    public AtividadeRecomendada getAtividadeRecomendada() {
        return atividadeRecomendada;
    }

    public void setAtividadeRecomendada(AtividadeRecomendada atividadeRecomendada) {
        this.atividadeRecomendada = atividadeRecomendada;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataAlerta() {
        return dataAlerta;
    }

    public void setDataAlerta(LocalDate dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    public StatusAlerta getStatus() {
        return status;
    }

    public void setStatus(StatusAlerta status) {
        this.status = status;
    }
}
