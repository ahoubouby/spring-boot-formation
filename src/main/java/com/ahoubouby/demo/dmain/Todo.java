package com.ahoubouby.demo.dmain;


import lombok.Data;
import lombok.Builder;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class Todo implements Serializable {
    @NotNull
    String id;
    @NotNull
    @NotBlank
    String desc;
    boolean completed;
    LocalDateTime created;
    LocalDateTime modified;
}
