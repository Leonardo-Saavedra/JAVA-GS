package br.com.fiap.corpcare.bo;

import br.com.fiap.corpcare.dao.AcaoRhDao;
import br.com.fiap.corpcare.model.AcaoRh;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class AcaoRhBo {

    @Inject
    AcaoRhDao acaoRhDao;

    public AcaoRh criar(AcaoRh a) throws SQLException {

        if (a.getRegistroRh() == null || a.getRegistroRh().getIdRh() == null) {
            throw new IllegalArgumentException("Registro de RH é obrigatório.");
        }
        if (a.getDecisaoRh() == null || a.getDecisaoRh().isBlank()) {
            throw new IllegalArgumentException("Decisão de RH é obrigatória.");
        }

        acaoRhDao.inserir(a);
        return a;
    }

    public List<AcaoRh> listar() throws SQLException {
        return acaoRhDao.listarTodos();
    }

    public AcaoRh buscarPorId(int id) throws SQLException {
        return acaoRhDao.buscarPorId(id);
    }

    public AcaoRh atualizar(int id, AcaoRh a) throws SQLException {

        AcaoRh existente = acaoRhDao.buscarPorId(id);

        if (existente == null) {
            throw new IllegalArgumentException("Ação de RH não encontrada.");
        }

        existente.setRegistroRh(a.getRegistroRh());
        existente.setDecisaoRh(a.getDecisaoRh());

        acaoRhDao.atualizar(existente);
        return existente;
    }

    public void excluir(int id) throws SQLException {
        acaoRhDao.excluir(id);
    }
}
