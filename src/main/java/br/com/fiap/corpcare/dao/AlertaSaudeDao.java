package br.com.fiap.corpcare.dao;

import br.com.fiap.corpcare.model.AlertaSaude;
import br.com.fiap.corpcare.model.AtividadeRecomendada;
import br.com.fiap.corpcare.model.InteracaoChatbot;
import br.com.fiap.corpcare.model.enums.StatusAlerta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AlertaSaudeDao {

    @Inject
    DataSource dataSource;

    public void inserir(AlertaSaude a) throws SQLException {
        String sql = """
                INSERT INTO TB_ALERTA_SAUDE
                (id_alerta, id_int_chatbot, id_atvd,
                 tipo_alerta, descricao, data_alerta, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getIdAlerta());
            ps.setInt(2, a.getInteracaoChatbot().getIdIntChatbot());
            ps.setInt(3, a.getAtividadeRecomendada().getIdAtividade());
            ps.setString(4, a.getTipoAlerta());
            ps.setString(5, a.getDescricao());
            ps.setDate(6, Date.valueOf(a.getDataAlerta()));

            ps.setString(7, a.getStatus().name());

            ps.executeUpdate();
        }
    }

    public List<AlertaSaude> listarTodos() throws SQLException {
        List<AlertaSaude> lista = new ArrayList<>();

        String sql = "SELECT * FROM TB_ALERTA_SAUDE ORDER BY id_alerta";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    public AlertaSaude buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM TB_ALERTA_SAUDE WHERE id_alerta = ?";

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

    public void atualizar(AlertaSaude a) throws SQLException {
        String sql = """
                UPDATE TB_ALERTA_SAUDE
                SET id_int_chatbot = ?, id_atvd = ?, tipo_alerta = ?, 
                    descricao = ?, data_alerta = ?, status = ?
                WHERE id_alerta = ?
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getInteracaoChatbot().getIdIntChatbot());
            ps.setInt(2, a.getAtividadeRecomendada().getIdAtividade());
            ps.setString(3, a.getTipoAlerta());
            ps.setString(4, a.getDescricao());
            ps.setDate(5, Date.valueOf(a.getDataAlerta()));
            ps.setString(6, a.getStatus().name());
            ps.setInt(7, a.getIdAlerta());

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM TB_ALERTA_SAUDE WHERE id_alerta = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private AlertaSaude mapRow(ResultSet rs) throws SQLException {

        AlertaSaude a = new AlertaSaude();

        a.setIdAlerta(rs.getInt("id_alerta"));

        InteracaoChatbot inter = new InteracaoChatbot();
        inter.setIdIntChatbot(rs.getInt("id_int_chatbot"));
        a.setInteracaoChatbot(inter);

        AtividadeRecomendada atv = new AtividadeRecomendada();
        atv.setIdAtividade(rs.getInt("id_atvd"));
        a.setAtividadeRecomendada(atv);

        a.setTipoAlerta(rs.getString("tipo_alerta"));
        a.setDescricao(rs.getString("descricao"));

        Date d = rs.getDate("data_alerta");
        a.setDataAlerta(d != null ? d.toLocalDate() : LocalDate.now());

        a.setStatus(StatusAlerta.valueOf(rs.getString("status")));

        return a;
    }
}
