package br.com.fiap.corpcare.model;

public class AcaoRh {

    private Integer idAcao;
    private RegistroRh registroRh;
    private String decisaoRh;

    public Integer getIdAcao() {
        return idAcao;
    }

    public void setIdAcao(Integer idAcao) {
        this.idAcao = idAcao;
    }

    public RegistroRh getRegistroRh() {
        return registroRh;
    }

    public void setRegistroRh(RegistroRh registroRh) {
        this.registroRh = registroRh;
    }

    public String getDecisaoRh() {
        return decisaoRh;
    }

    public void setDecisaoRh(String decisaoRh) {
        this.decisaoRh = decisaoRh;
    }
}
