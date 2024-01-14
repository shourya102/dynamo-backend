package com.dynamo.dynamo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Parameter {

    @Id
    private Long id;
    private String name;
    private Type type;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemDetails problemDetails;

    public Parameter(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}
