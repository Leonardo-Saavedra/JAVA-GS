package br.com.fiap.corpcare.bo;

import br.com.fiap.corpcare.dao.AlertaSaudeDao;
import br.com.fiap.corpcare.model.AlertaSaude;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class AlertaSaudeBo {

    @Inject
    AlertaSaudeDao alertaSaudeDao;

    public AlertaSaude criar(AlertaSaude a) throws SQLException {

        if (a.getInteracaoChatbot() == null || a.getInteracaoChatbot().getIdIntChatbot() == null) {
            throw new IllegalArgumentException("Interação do chatbot é obrigatória.");
        }
        if (a.getAtividadeRecomendada() == null || a.getAtividadeRecomendada().getIdAtividade() == null) {
            throw new IllegalArgumentException("Atividade recomendada é obrigatória.");
        }
        if (a.getTipoAlerta() == null || a.getTipoAlerta().isBlank()) {
            throw new IllegalArgumentException("Tipo do alerta é obrigatório.");
        }
        if (a.getDescricao() == null || a.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição do alerta é obrigatória.");
        }
        if (a.getStatus() == null) {
            throw new IllegalArgumentException("Status do alerta é obrigatório.");
        }

        alertaSaudeDao.inserir(a);
        return a;
    }

    public List<AlertaSaude> listar() throws SQLException {
        return alertaSaudeDao.listarTodos();
    }

    public AlertaSaude buscarPorId(int id) throws SQLException {
        return alertaSaudeDao.buscarPorId(id);
    }

    public AlertaSaude atualizar(int id, AlertaSaude a) throws SQLException {

        AlertaSaude existente = alertaSaudeDao.buscarPorId(id);

        if (existente == null) {
            throw new IllegalArgumentException("Alerta de saúde não encontrado.");
        }

        existente.setInteracaoChatbot(a.getInteracaoChatbot());
        existente.setAtividadeRecomendada(a.getAtividadeRecomendada());
        existente.setTipoAlerta(a.getTipoAlerta());
        existente.setDescricao(a.getDescricao());
        existente.setDataAlerta(a.getDataAlerta());
        existente.setStatus(a.getStatus());

        alertaSaudeDao.atualizar(existente);
        return existente;
    }

    public void excluir(int id) throws SQLException {
        alertaSaudeDao.excluir(id);
    }
}
