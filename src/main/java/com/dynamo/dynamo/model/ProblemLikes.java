package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProblemLikes extends Likes{

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;


}
