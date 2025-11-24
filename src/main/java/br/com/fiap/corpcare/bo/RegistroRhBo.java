package br.com.fiap.corpcare.bo;

import br.com.fiap.corpcare.dao.RegistroRhDao;
import br.com.fiap.corpcare.model.RegistroRh;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class RegistroRhBo {

    @Inject
    RegistroRhDao registroRhDao;

    public RegistroRh criar(RegistroRh r) throws SQLException {

        if (r.getAlertaSaude() == null || r.getAlertaSaude().getIdAlerta() == null) {
            throw new IllegalArgumentException("Alerta de saúde é obrigatório.");
        }
        if (r.getNomeResponsavel() == null || r.getNomeResponsavel().isBlank()) {
            throw new IllegalArgumentException("Nome do responsável é obrigatório.");
        }

        registroRhDao.inserir(r);
        return r;
    }

    public List<RegistroRh> listar() throws SQLException {
        return registroRhDao.listarTodos();
    }

    public RegistroRh buscarPorId(int id) throws SQLException {
        return registroRhDao.buscarPorId(id);
    }

    public RegistroRh atualizar(int id, RegistroRh r) throws SQLException {

        RegistroRh existente = registroRhDao.buscarPorId(id);

        if (existente == null) {
            throw new IllegalArgumentException("Registro de RH não encontrado.");
        }

        existente.setAlertaSaude(r.getAlertaSaude());
        existente.setNomeResponsavel(r.getNomeResponsavel());
        existente.setFeedbackColaborador(r.getFeedbackColaborador());

        registroRhDao.atualizar(existente);
        return existente;
    }

    public void excluir(int id) throws SQLException {
        registroRhDao.excluir(id);
    }
}
