package br.com.fiap.corpcare.resource;

import br.com.fiap.corpcare.bo.InteracaoChatbotBo;
import br.com.fiap.corpcare.model.InteracaoChatbot;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/interacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InteracaoChatbotResource {

    @Inject
    InteracaoChatbotBo interacaoChatbotBo;

    @GET
    public Response listar() {
        try {
            List<InteracaoChatbot> lista = interacaoChatbotBo.listar();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) {
        try {
            InteracaoChatbot i = interacaoChatbotBo.buscarPorId(id);
            if (i == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.ok(i).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criar(InteracaoChatbot i, @Context UriInfo uriInfo) {
        try {
            interacaoChatbotBo.criar(i);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(i.getIdIntChatbot()))
                    .build();
            return Response.created(uri).entity(i).build();

        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, InteracaoChatbot i) {
        try {
            InteracaoChatbot atualizado = interacaoChatbotBo.atualizar(id, i);
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
            interacaoChatbotBo.excluir(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
