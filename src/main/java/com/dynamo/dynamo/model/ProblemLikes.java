package com.dynamo.dynamo.model;

import com.dynamo.dynamo.model.problem.Problem;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProblemLikes extends Likes{

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;


}
