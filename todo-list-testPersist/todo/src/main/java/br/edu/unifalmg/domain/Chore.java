package br.edu.unifalmg.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chore {

    private String description;

    private Boolean isCompleted;

    private LocalDate deadline;

    private Long id;

    public Chore(String description, Boolean isCompleted, LocalDate deadline) {
        this.description = description;
        this.isCompleted = isCompleted;
        this.deadline = deadline;
    }
}
