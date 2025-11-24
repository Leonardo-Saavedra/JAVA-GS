package br.com.fiap.corpcare.model;

public class RegistroRh {

    private Integer idRh;
    private AlertaSaude alertaSaude;
    private String nomeResponsavel;
    private String feedbackColaborador;

    public Integer getIdRh() {
        return idRh;
    }

    public void setIdRh(Integer idRh) {
        this.idRh = idRh;
    }

    public AlertaSaude getAlertaSaude() {
        return alertaSaude;
    }

    public void setAlertaSaude(AlertaSaude alertaSaude) {
        this.alertaSaude = alertaSaude;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getFeedbackColaborador() {
        return feedbackColaborador;
    }

    public void setFeedbackColaborador(String feedbackColaborador) {
        this.feedbackColaborador = feedbackColaborador;
    }
}
