package br.com.fiap.corpcare.dao;

import br.com.fiap.corpcare.model.AcaoRh;
import br.com.fiap.corpcare.model.RegistroRh;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AcaoRhDao {

    @Inject
    DataSource dataSource;

    public void inserir(AcaoRh a) throws SQLException {
        String sql = """
                INSERT INTO TB_ACAO
                (id_acao, id_rh, decisao_rh)
                VALUES (?, ?, ?)
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getIdAcao());
            ps.setInt(2, a.getRegistroRh().getIdRh());
            ps.setString(3, a.getDecisaoRh());

            ps.executeUpdate();
        }
    }

    public List<AcaoRh> listarTodos() throws SQLException {
        List<AcaoRh> lista = new ArrayList<>();
        String sql = "SELECT * FROM TB_ACAO ORDER BY id_acao";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    public AcaoRh buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM TB_ACAO WHERE id_acao = ?";

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

    public void atualizar(AcaoRh a) throws SQLException {
        String sql = """
                UPDATE TB_ACAO
                SET id_rh = ?, decisao_rh = ?
                WHERE id_acao = ?
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getRegistroRh().getIdRh());
            ps.setString(2, a.getDecisaoRh());
            ps.setInt(3, a.getIdAcao());

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM TB_ACAO WHERE id_acao = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private AcaoRh mapRow(ResultSet rs) throws SQLException {
        AcaoRh a = new AcaoRh();

        a.setIdAcao(rs.getInt("id_acao"));

        RegistroRh registro = new RegistroRh();
        registro.setIdRh(rs.getInt("id_rh"));
        a.setRegistroRh(registro);

        a.setDecisaoRh(rs.getString("decisao_rh"));

        return a;
    }
}
