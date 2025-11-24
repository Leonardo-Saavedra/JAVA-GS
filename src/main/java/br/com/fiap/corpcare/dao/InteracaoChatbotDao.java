package br.com.fiap.corpcare.dao;

import br.com.fiap.corpcare.model.Colaborador;
import br.com.fiap.corpcare.model.InteracaoChatbot;
import br.com.fiap.corpcare.model.enums.Sentimento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InteracaoChatbotDao {

    @Inject
    DataSource dataSource;

    public void inserir(InteracaoChatbot i) throws SQLException {
        String sql = """
                INSERT INTO TB_INT_CHATBOT
                (id_int_chatbot, id_colaborador, data_interacao, mensagem, sentimento, nivel_estresse)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, i.getIdIntChatbot());
            ps.setInt(2, i.getColaborador().getIdColaborador());
            ps.setTimestamp(3, Timestamp.valueOf(i.getDataInteracao()));
            ps.setString(4, i.getMensagem());

            String sentimentoDb = switch (i.getSentimento()) {
                case POSITIVO -> "Positivo";
                case NEUTRO -> "Neutro";
                case NEGATIVO -> "Negativo";
            };
            ps.setString(5, sentimentoDb);

            ps.setInt(6, i.getNivelEstresse());

            ps.executeUpdate();
        }
    }

    public List<InteracaoChatbot> listarTodos() throws SQLException {
        List<InteracaoChatbot> lista = new ArrayList<>();

        String sql = "SELECT * FROM TB_INT_CHATBOT ORDER BY id_int_chatbot";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    public InteracaoChatbot buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM TB_INT_CHATBOT WHERE id_int_chatbot = ?";

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

    public void atualizar(InteracaoChatbot i) throws SQLException {
        String sql = """
                UPDATE TB_INT_CHATBOT
                SET id_colaborador = ?, data_interacao = ?, mensagem = ?, sentimento = ?, nivel_estresse = ?
                WHERE id_int_chatbot = ?
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, i.getColaborador().getIdColaborador());
            ps.setTimestamp(2, Timestamp.valueOf(i.getDataInteracao()));
            ps.setString(3, i.getMensagem());

            String sentimentoDb = switch (i.getSentimento()) {
                case POSITIVO -> "Positivo";
                case NEUTRO -> "Neutro";
                case NEGATIVO -> "Negativo";
            };

            ps.setString(4, sentimentoDb);
            ps.setInt(5, i.getNivelEstresse());
            ps.setInt(6, i.getIdIntChatbot());

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM TB_INT_CHATBOT WHERE id_int_chatbot = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private InteracaoChatbot mapRow(ResultSet rs) throws SQLException {
        InteracaoChatbot i = new InteracaoChatbot();

        i.setIdIntChatbot(rs.getInt("id_int_chatbot"));

        Colaborador c = new Colaborador();
        c.setIdColaborador(rs.getInt("id_colaborador"));
        i.setColaborador(c);

        Timestamp ts = rs.getTimestamp("data_interacao");
        i.setDataInteracao(ts != null ? ts.toLocalDateTime() : LocalDateTime.now());

        i.setMensagem(rs.getString("mensagem"));

        String sentimentoDb = rs.getString("sentimento");
        Sentimento enumSent = switch (sentimentoDb) {
            case "Positivo" -> Sentimento.POSITIVO;
            case "Neutro" -> Sentimento.NEUTRO;
            case "Negativo" -> Sentimento.NEGATIVO;
            default -> null;
        };

        i.setSentimento(enumSent);
        i.setNivelEstresse(rs.getInt("nivel_estresse"));

        return i;
    }
}
