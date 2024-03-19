package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SolutionLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solution_id")
    private Solution solution;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
