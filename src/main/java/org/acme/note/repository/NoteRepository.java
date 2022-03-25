package org.acme.note.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.note.entity.Note;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class NoteRepository implements PanacheRepository<Note>{
    public PanacheQuery<Note> findByContent(String content) {
        System.out.println("asdasd");
        System.out.println(content);
        return find("content like concat('%', :content, '%')", Sort.descending("created_at"), Parameters.with("content", content).map());
    }
}
