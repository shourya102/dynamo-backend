package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SolutionLikes extends Likes {


    @ManyToOne
    @JoinColumn(name = "solution_id")
    private Solution solution;


}
