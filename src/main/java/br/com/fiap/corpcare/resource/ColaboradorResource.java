package br.com.fiap.corpcare.resource;

import br.com.fiap.corpcare.bo.ColaboradorBo;
import br.com.fiap.corpcare.model.Colaborador;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/colaboradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColaboradorResource {

    @Inject
    ColaboradorBo colaboradorBo;

    @GET
    public Response listar() {
        try {
            List<Colaborador> lista = colaboradorBo.listar();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        try {
            Colaborador c = colaboradorBo.buscarPorId(id);

            if (c == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.ok(c).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criar(Colaborador c, @Context UriInfo uriInfo) {
        try {
            colaboradorBo.criar(c);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(c.getIdColaborador()))
                    .build();

            return Response.created(uri).entity(c).build();

        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();

        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Colaborador c) {
        try {
            Colaborador atualizado = colaboradorBo.atualizar(id, c);
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
            colaboradorBo.excluir(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
