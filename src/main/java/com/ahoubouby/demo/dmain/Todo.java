package com.ahoubouby.demo.dmain;


import lombok.Data;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
public class Todo implements Serializable {
    @NotNull
    @Id
    String id;
    @NotNull
    @NotBlank
    String desc;
    boolean completed;
    LocalDateTime created;
    LocalDateTime modified;

    public Todo() {
        LocalDateTime date = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.created = date;
        this.modified = date;
    }

    public Todo(String description) {
        this();
        this.desc = description;
    }
}
