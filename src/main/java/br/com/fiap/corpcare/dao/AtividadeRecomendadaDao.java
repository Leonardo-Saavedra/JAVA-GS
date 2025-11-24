package br.com.fiap.corpcare.dao;

import br.com.fiap.corpcare.model.AtividadeRecomendada;
import br.com.fiap.corpcare.model.InteracaoChatbot;
import br.com.fiap.corpcare.model.enums.CategoriaAtividade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AtividadeRecomendadaDao {

    @Inject
    DataSource dataSource;

    public void inserir(AtividadeRecomendada a) throws SQLException {

        String sql = """
                INSERT INTO TB_ATVD_RECOMANDADA
                (id_atvd, id_int_chatbot, nome, categoria, descricao)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getIdAtividade());
            ps.setInt(2, a.getInteracaoChatbot().getIdIntChatbot());
            ps.setString(3, a.getNome());

            String categoriaDb = switch (a.getCategoria()) {
                case RELAXAMENTO -> "Relaxamento";
                case ALONGAMENTO -> "Alongamento";
                case MINDFULNESS -> "Mindfulness";
                case RESPIRACAO -> "Respiração";
            };

            ps.setString(4, categoriaDb);
            ps.setString(5, a.getDescricao());

            ps.executeUpdate();
        }
    }

    public List<AtividadeRecomendada> listarTodos() throws SQLException {
        List<AtividadeRecomendada> lista = new ArrayList<>();
        String sql = "SELECT * FROM TB_ATVD_RECOMANDADA ORDER BY id_atvd";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    public AtividadeRecomendada buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM TB_ATVD_RECOMANDADA WHERE id_atvd = ?";

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

    public void atualizar(AtividadeRecomendada a) throws SQLException {
        String sql = """
                UPDATE TB_ATVD_RECOMANDADA
                SET id_int_chatbot = ?, nome = ?, categoria = ?, descricao = ?
                WHERE id_atvd = ?
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getInteracaoChatbot().getIdIntChatbot());
            ps.setString(2, a.getNome());

            String categoriaDb = switch (a.getCategoria()) {
                case RELAXAMENTO -> "Relaxamento";
                case ALONGAMENTO -> "Alongamento";
                case MINDFULNESS -> "Mindfulness";
                case RESPIRACAO -> "Respiração";
            };

            ps.setString(3, categoriaDb);
            ps.setString(4, a.getDescricao());
            ps.setInt(5, a.getIdAtividade());

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM TB_ATVD_RECOMANDADA WHERE id_atvd = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private AtividadeRecomendada mapRow(ResultSet rs) throws SQLException {
        AtividadeRecomendada a = new AtividadeRecomendada();

        a.setIdAtividade(rs.getInt("id_atvd"));

        InteracaoChatbot interacao = new InteracaoChatbot();
        interacao.setIdIntChatbot(rs.getInt("id_int_chatbot"));
        a.setInteracaoChatbot(interacao);

        a.setNome(rs.getString("nome"));

        String categoriaDb = rs.getString("categoria");

        CategoriaAtividade categoriaEnum = switch (categoriaDb) {
            case "Relaxamento" -> CategoriaAtividade.RELAXAMENTO;
            case "Alongamento" -> CategoriaAtividade.ALONGAMENTO;
            case "Mindfulness" -> CategoriaAtividade.MINDFULNESS;
            case "Respiração" -> CategoriaAtividade.RESPIRACAO;
            default -> null;
        };

        a.setCategoria(categoriaEnum);
        a.setDescricao(rs.getString("descricao"));

        return a;
    }
}
