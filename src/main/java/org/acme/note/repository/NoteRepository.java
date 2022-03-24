package org.acme.note.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.note.entity.Note;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class NoteRepository implements PanacheRepository<Note>{
    
}
