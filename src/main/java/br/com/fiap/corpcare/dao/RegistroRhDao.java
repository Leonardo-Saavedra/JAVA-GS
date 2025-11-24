package br.com.fiap.corpcare.dao;

import br.com.fiap.corpcare.model.AlertaSaude;
import br.com.fiap.corpcare.model.RegistroRh;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RegistroRhDao {

    @Inject
    DataSource dataSource;

    public void inserir(RegistroRh r) throws SQLException {

        String sql = """
                INSERT INTO TB_RH
                (id_rh, id_alerta, nome_responsavel, feedback_colaborador)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getIdRh());
            ps.setInt(2, r.getAlertaSaude().getIdAlerta());
            ps.setString(3, r.getNomeResponsavel());
            ps.setString(4, r.getFeedbackColaborador());

            ps.executeUpdate();
        }
    }

    public List<RegistroRh> listarTodos() throws SQLException {
        List<RegistroRh> lista = new ArrayList<>();

        String sql = "SELECT * FROM TB_RH ORDER BY id_rh";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    public RegistroRh buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM TB_RH WHERE id_rh = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    public void atualizar(RegistroRh r) throws SQLException {
        String sql = """
                UPDATE TB_RH
                SET id_alerta = ?, nome_responsavel = ?, feedback_colaborador = ?
                WHERE id_rh = ?
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getAlertaSaude().getIdAlerta());
            ps.setString(2, r.getNomeResponsavel());
            ps.setString(3, r.getFeedbackColaborador());
            ps.setInt(4, r.getIdRh());

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM TB_RH WHERE id_rh = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private RegistroRh mapRow(ResultSet rs) throws SQLException {
        RegistroRh r = new RegistroRh();

        r.setIdRh(rs.getInt("id_rh"));

        AlertaSaude alerta = new AlertaSaude();
        alerta.setIdAlerta(rs.getInt("id_alerta"));
        r.setAlertaSaude(alerta);

        r.setNomeResponsavel(rs.getString("nome_responsavel"));
        r.setFeedbackColaborador(rs.getString("feedback_colaborador"));

        return r;
    }
}
