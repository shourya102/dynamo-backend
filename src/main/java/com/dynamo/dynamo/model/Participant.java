package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Participant {



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
  private Long ranks;
  @ManyToOne
  private User user;
  @ManyToOne
  private Contest contest;
    @OneToMany
  private List<Problem> solvedProblemList;
}
