package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private EType type;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemDetails problemDetails;

    public Parameter(String name, EType type) {
        this.name = name;
        this.type = type;
    }
}
