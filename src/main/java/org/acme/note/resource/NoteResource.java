package org.acme.note.resource;

import java.util.Optional;

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
import javax.ws.rs.core.Response.Status;

import org.acme.note.entity.Note;
import org.acme.note.repository.NoteRepository;



@Path("notes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteResource {
    
    @Inject
    NoteRepository noteRepository;

    @GET
    public Response list(
            @QueryParam("page_index") @DefaultValue("0") int page_index,
            @QueryParam("page_size") @DefaultValue("8") int page_size,
            @QueryParam("content") @DefaultValue("") String content
        ) {
        
        return Response.ok(noteRepository.findByContent(content).page(page_index, page_size).list()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        
        return noteRepository.findByIdOptional(id)
            .map(record -> Response.ok(record).build())
            .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response create(Note note) {
        noteRepository.persist(note);
        if (noteRepository.isPersistent(note)) {
            return Response.ok(note).status(Status.CREATED).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Note note) {
        return noteRepository.findByIdOptional(id)
        .map(record -> {
            record.setContent(note.getContent());
            noteRepository.persist(record);
            return Response.ok(record).build();
        })
        .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        return noteRepository.findByIdOptional(id)
        .map(record -> {
            noteRepository.deleteById(id);
            return Response.ok(record).status(Status.NO_CONTENT).build();
        })
        .orElse(Response.status(Status.NOT_FOUND).build());
       
    }
}
