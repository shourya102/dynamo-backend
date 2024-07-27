package com.dynamo.dynamo.model.problem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String inputs;
    private String output;
    @ManyToOne
    @JoinColumn(name = "problem_details_id")
    private ProblemDetails problemDetails;

    public TestCase(String inputs, String output, ProblemDetails problemDetails) {
        this.inputs = inputs;
        this.output = output;
        this.problemDetails = problemDetails;
    }
}
