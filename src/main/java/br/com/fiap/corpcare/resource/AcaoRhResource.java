package br.com.fiap.corpcare.resource;

import br.com.fiap.corpcare.bo.AcaoRhBo;
import br.com.fiap.corpcare.model.AcaoRh;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/acoes-rh")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AcaoRhResource {

    @Inject
    AcaoRhBo acaoRhBo;

    @GET
    public Response listar() {
        try {
            List<AcaoRh> lista = acaoRhBo.listar();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        try {
            AcaoRh a = acaoRhBo.buscarPorId(id);
            if (a == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.ok(a).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criar(AcaoRh a, @Context UriInfo uriInfo) {
        try {
            acaoRhBo.criar(a);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(a.getIdAcao()))
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
    public Response atualizar(@PathParam("id") int id, AcaoRh a) {
        try {
            AcaoRh atualizado = acaoRhBo.atualizar(id, a);
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
            acaoRhBo.excluir(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
