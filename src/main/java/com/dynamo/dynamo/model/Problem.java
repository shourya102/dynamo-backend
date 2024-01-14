package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private Difficulty difficulty;

    @OneToOne(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProblemDetails problemDetails;

    @NotEmpty
    private Integer numberOfAttempts;

    @NotEmpty
    private Integer successfulAttempts;

    @NotEmpty
    private Integer likes;

    @NotEmpty
    private Integer dislikes;

    @ManyToMany
    @JoinTable(
            name = "problem_topics",
            joinColumns = @JoinColumn(name = "problem_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Set<Topic> topic;


    public Problem(String name, Difficulty difficulty, Integer numberOfAttempts, Integer successfulAttempts, Integer likes, Integer dislikes) {
        this.name = name;
        this.difficulty = difficulty;
        this.numberOfAttempts = numberOfAttempts;
        this.successfulAttempts = successfulAttempts;
        this.likes = likes;
        this.dislikes = dislikes;
    }
}
