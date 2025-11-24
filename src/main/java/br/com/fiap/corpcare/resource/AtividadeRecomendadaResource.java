package br.com.fiap.corpcare.resource;

import br.com.fiap.corpcare.bo.AtividadeRecomendadaBo;
import br.com.fiap.corpcare.model.AtividadeRecomendada;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/atividades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtividadeRecomendadaResource {

    @Inject
    AtividadeRecomendadaBo atividadeRecomendadaBo;

    @GET
    public Response listar() {
        try {
            List<AtividadeRecomendada> lista = atividadeRecomendadaBo.listar();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        try {
            AtividadeRecomendada a = atividadeRecomendadaBo.buscarPorId(id);
            if (a == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.ok(a).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criar(AtividadeRecomendada a, @Context UriInfo uriInfo) {
        try {
            atividadeRecomendadaBo.criar(a);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(a.getIdAtividade()))
                    .build();
            return Response.created(uri).entity(a).build();

        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, AtividadeRecomendada a) {
        try {
            AtividadeRecomendada atualizado = atividadeRecomendadaBo.atualizar(id, a);
            return Response.ok(atualizado).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") int id) {
        try {
            atividadeRecomendadaBo.excluir(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
