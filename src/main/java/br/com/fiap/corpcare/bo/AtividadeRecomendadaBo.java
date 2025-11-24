package br.com.fiap.corpcare.bo;

import br.com.fiap.corpcare.dao.AtividadeRecomendadaDao;
import br.com.fiap.corpcare.model.AtividadeRecomendada;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class AtividadeRecomendadaBo {

    @Inject
    AtividadeRecomendadaDao atividadeRecomendadaDao;

    public AtividadeRecomendada criar(AtividadeRecomendada a) throws SQLException {

        if (a.getInteracaoChatbot() == null || a.getInteracaoChatbot().getIdIntChatbot() == null) {
            throw new IllegalArgumentException("Interação do chatbot é obrigatória.");
        }
        if (a.getNome() == null || a.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da atividade é obrigatório.");
        }
        if (a.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria da atividade é obrigatória.");
        }

        atividadeRecomendadaDao.inserir(a);
        return a;
    }

    public List<AtividadeRecomendada> listar() throws SQLException {
        return atividadeRecomendadaDao.listarTodos();
    }

    public AtividadeRecomendada buscarPorId(int id) throws SQLException {
        return atividadeRecomendadaDao.buscarPorId(id);
    }

    public AtividadeRecomendada atualizar(int id, AtividadeRecomendada a) throws SQLException {

        AtividadeRecomendada existente = atividadeRecomendadaDao.buscarPorId(id);

        if (existente == null) {
            throw new IllegalArgumentException("Atividade recomendada não encontrada.");
        }

        existente.setInteracaoChatbot(a.getInteracaoChatbot());
        existente.setNome(a.getNome());
        existente.setCategoria(a.getCategoria());
        existente.setDescricao(a.getDescricao());

        atividadeRecomendadaDao.atualizar(existente);
        return existente;
    }

    public void excluir(int id) throws SQLException {
        atividadeRecomendadaDao.excluir(id);
    }
}
