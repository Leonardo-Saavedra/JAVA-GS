package br.com.fiap.corpcare.resource;

import br.com.fiap.corpcare.dao.ColaboradorDao;
import br.com.fiap.corpcare.model.Colaborador;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/testar-dao")
public class TesteDaoResource {

    @Inject
    ColaboradorDao colaboradorDao;

    @GET
    public Response testar() {
        try {
            List<Colaborador> lista = colaboradorDao.listarTodos();
            return Response.ok("Total colaboradores: " + lista.size()).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("Erro no DAO: " + e.getMessage())
                    .build();
        }
    }
}
