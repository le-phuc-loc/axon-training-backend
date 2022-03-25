package org.acme.note.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class Note {
    @Id @GeneratedValue private Long id;
    private String content;
    private LocalDateTime created_at;


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getCreatedAt(){
        return created_at.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    public void setCreatedAt(LocalDateTime created_at){
        this.created_at = created_at;
    }


    @PrePersist
    public void prePersist() {
        this.created_at = LocalDateTime.now();
    }

}
