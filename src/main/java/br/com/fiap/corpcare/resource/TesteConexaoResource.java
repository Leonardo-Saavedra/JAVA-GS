package br.com.fiap.corpcare.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;

@Path("/testar-conexao")
public class TesteConexaoResource {

    @Inject
    DataSource dataSource;

    @GET
    public Response testar() {
        try (Connection conn = dataSource.getConnection()) {
            return Response.ok("Conectado com sucesso! " + conn.getMetaData().getURL()).build();
        } catch (Exception e) {
            return Response.status(500).entity("Erro ao conectar: " + e.getMessage()).build();
        }
    }
}
