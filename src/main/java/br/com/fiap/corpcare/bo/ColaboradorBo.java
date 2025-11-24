package br.com.fiap.corpcare.bo;

import br.com.fiap.corpcare.dao.ColaboradorDao;
import br.com.fiap.corpcare.model.Colaborador;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ColaboradorBo {

    @Inject
    ColaboradorDao colaboradorDao;

    public Colaborador criar(Colaborador c) throws SQLException {

        if (c.getNomeColaborador() == null || c.getNomeColaborador().isBlank()) {
            throw new IllegalArgumentException("Nome do colaborador é obrigatório.");
        }

        if (c.getEmailColaborador() == null || !c.getEmailColaborador().contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        colaboradorDao.inserir(c);

        return c;
    }

    public List<Colaborador> listar() throws SQLException {
        return colaboradorDao.listarTodos();
    }

    public Colaborador buscarPorId(int id) throws SQLException {
        return colaboradorDao.buscarPorId(id);
    }

    public Colaborador atualizar(int id, Colaborador c) throws SQLException {

        Colaborador existente = colaboradorDao.buscarPorId(id);

        if (existente == null) {
            throw new IllegalArgumentException("Colaborador não encontrado.");
        }

        existente.setNomeColaborador(c.getNomeColaborador());
        existente.setEmailColaborador(c.getEmailColaborador());
        existente.setCargo(c.getCargo());

        colaboradorDao.atualizar(existente);

        return existente;
    }

    public void excluir(int id) throws SQLException {
        colaboradorDao.excluir(id);
    }
}
