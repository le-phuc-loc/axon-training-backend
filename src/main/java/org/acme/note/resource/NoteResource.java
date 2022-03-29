package org.acme.note.resource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.note.entity.Note;
import org.acme.note.service.NoteService;

@Path("notes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteResource {

    @Inject
    NoteService noteService;

    @GET
    public Response list(
            @QueryParam("page_index") @DefaultValue("0") int page_index,
            @QueryParam("page_size") @DefaultValue("8") int page_size,
            @QueryParam("content") @DefaultValue("") String content) {

        return noteService.list(page_index, page_size, content);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {

        return noteService.get(id);
    }

    @POST
    @Transactional
    public Response create(Note note) {
        return noteService.create(note);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Note note) {
        return noteService.update(id, note);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        return noteService.delete(id);
    }
}
