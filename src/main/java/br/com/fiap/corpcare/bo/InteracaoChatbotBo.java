package br.com.fiap.corpcare.bo;

import br.com.fiap.corpcare.dao.InteracaoChatbotDao;
import br.com.fiap.corpcare.model.InteracaoChatbot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class InteracaoChatbotBo {

    @Inject
    InteracaoChatbotDao interacaoChatbotDao;

    public InteracaoChatbot criar(InteracaoChatbot i) throws SQLException {

        if (i.getColaborador() == null || i.getColaborador().getIdColaborador() == null) {
            throw new IllegalArgumentException("Colaborador é obrigatório.");
        }
        if (i.getMensagem() == null || i.getMensagem().isBlank()) {
            throw new IllegalArgumentException("Mensagem é obrigatória.");
        }
        if (i.getNivelEstresse() == null || i.getNivelEstresse() < 0 || i.getNivelEstresse() > 100) {
            throw new IllegalArgumentException("Nível de estresse deve estar entre 0 e 100.");
        }
        if (i.getSentimento() == null) {
            throw new IllegalArgumentException("Sentimento é obrigatório.");
        }

        interacaoChatbotDao.inserir(i);
        return i;
    }

    public List<InteracaoChatbot> listar() throws SQLException {
        return interacaoChatbotDao.listarTodos();
    }

    public InteracaoChatbot buscarPorId(int id) throws SQLException {
        return interacaoChatbotDao.buscarPorId(id);
    }

    public InteracaoChatbot atualizar(int id, InteracaoChatbot i) throws SQLException {
        InteracaoChatbot existente = interacaoChatbotDao.buscarPorId(id);

        if (existente == null) {
            throw new IllegalArgumentException("Interação não encontrada.");
        }

        existente.setColaborador(i.getColaborador());
        existente.setDataInteracao(i.getDataInteracao());
        existente.setMensagem(i.getMensagem());
        existente.setSentimento(i.getSentimento());
        existente.setNivelEstresse(i.getNivelEstresse());

        interacaoChatbotDao.atualizar(existente);
        return existente;
    }

    public void excluir(int id) throws SQLException {
        interacaoChatbotDao.excluir(id);
    }
}
