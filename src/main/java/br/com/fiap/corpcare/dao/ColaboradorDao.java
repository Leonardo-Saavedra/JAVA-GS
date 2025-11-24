package br.com.fiap.corpcare.dao;

import br.com.fiap.corpcare.model.Colaborador;
import br.com.fiap.corpcare.model.enums.Cargo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ColaboradorDao {

    @Inject
    DataSource dataSource;

    public void inserir(Colaborador c) throws SQLException {
        String sql = """
                INSERT INTO TB_COLABORADOR
                (id_colaborador, nome_colaborador, email_colaborador, cargo)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getIdColaborador());
            ps.setString(2, c.getNomeColaborador());
            ps.setString(3, c.getEmailColaborador());

            String cargoDb = switch (c.getCargo()) {
                case ANALISTA -> "Analista";
                case DESENVOLVEDOR -> "Desenvolvedor";
                case GERENTE -> "Gerente";
                case COORDENADOR -> "Coordenador";
            };

            ps.setString(4, cargoDb);
            ps.executeUpdate();
        }
    }

    public List<Colaborador> listarTodos() throws SQLException {
        List<Colaborador> lista = new ArrayList<>();

        String sql = "SELECT * FROM TB_COLABORADOR ORDER BY id_colaborador";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }

        return lista;
    }

    public Colaborador buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM TB_COLABORADOR WHERE id_colaborador = ?";

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

    public void atualizar(Colaborador c) throws SQLException {
        String sql = """
                UPDATE TB_COLABORADOR
                SET nome_colaborador = ?, email_colaborador = ?, cargo = ?
                WHERE id_colaborador = ?
                """;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String cargoDb = switch (c.getCargo()) {
                case ANALISTA -> "Analista";
                case DESENVOLVEDOR -> "Desenvolvedor";
                case GERENTE -> "Gerente";
                case COORDENADOR -> "Coordenador";
            };

            ps.setString(1, c.getNomeColaborador());
            ps.setString(2, c.getEmailColaborador());
            ps.setString(3, cargoDb);
            ps.setInt(4, c.getIdColaborador());

            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM TB_COLABORADOR WHERE id_colaborador = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Colaborador mapRow(ResultSet rs) throws SQLException {
        Colaborador c = new Colaborador();

        c.setIdColaborador(rs.getInt("id_colaborador"));
        c.setNomeColaborador(rs.getString("nome_colaborador"));
        c.setEmailColaborador(rs.getString("email_colaborador"));

        String cargoDb = rs.getString("cargo");

        Cargo cargoEnum = switch (cargoDb) {
            case "Analista" -> Cargo.ANALISTA;
            case "Desenvolvedor" -> Cargo.DESENVOLVEDOR;
            case "Gerente" -> Cargo.GERENTE;
            case "Coordenador" -> Cargo.COORDENADOR;
            default -> null;
        };

        c.setCargo(cargoEnum);

        return c;
    }
}
