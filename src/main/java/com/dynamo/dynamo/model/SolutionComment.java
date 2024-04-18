package com.dynamo.dynamo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class SolutionComment extends Comments {

    @ManyToOne()
    @JoinColumn(name = "solution_id")
  private   Solution solution;
}
