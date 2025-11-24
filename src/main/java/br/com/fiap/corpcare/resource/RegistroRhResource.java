package br.com.fiap.corpcare.resource;

import br.com.fiap.corpcare.bo.RegistroRhBo;
import br.com.fiap.corpcare.model.RegistroRh;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/registros-rh")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistroRhResource {

    @Inject
    RegistroRhBo registroRhBo;

    @GET
    public Response listar() {
        try {
            List<RegistroRh> lista = registroRhBo.listar();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        try {
            RegistroRh r = registroRhBo.buscarPorId(id);
            if (r == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.ok(r).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criar(RegistroRh r, @Context UriInfo uriInfo) {
        try {
            registroRhBo.criar(r);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(r.getIdRh()))
                    .build();
            return Response.created(uri).entity(r).build();

        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, RegistroRh r) {
        try {
            RegistroRh atualizado = registroRhBo.atualizar(id, r);
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
            registroRhBo.excluir(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
