package org.acme.note.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.note.entity.Note;
import org.acme.note.repository.NoteRepository;

@ApplicationScoped
public class NoteService {
    @Inject
    NoteRepository noteRepository;

    public Response list(
            int page_index,
            int page_size,
            String content) {
        return Response.ok(noteRepository.findByContent(content).page(page_index, page_size).list()).build();
    }

    public Response get(Long id) {
        return noteRepository.findByIdOptional(id)
                .map(record -> Response.ok(record).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    public Response create(Note note) {
        noteRepository.persist(note);
        if (noteRepository.isPersistent(note)) {
            return Response.ok(note).status(Status.CREATED).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    public Response update(Long id, Note note) {
        return noteRepository.findByIdOptional(id)
                .map(record -> {
                    record.setContent(note.getContent());
                    noteRepository.persist(record);
                    return Response.ok(record).build();
                })
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    public Response delete(Long id) {
        return noteRepository.findByIdOptional(id)
                .map(record -> {
                    noteRepository.deleteById(id);
                    return Response.ok(record).status(Status.NO_CONTENT).build();
                })
                .orElse(Response.status(Status.NOT_FOUND).build());

    }

}
