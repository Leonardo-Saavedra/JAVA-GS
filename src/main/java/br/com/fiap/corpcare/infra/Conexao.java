package br.com.fiap.corpcare.infra;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {

    private static DataSource dataSource;

    public static void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
