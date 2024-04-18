package com.dynamo.dynamo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // @Column(nullable = false, unique = true)
    private Long contestId;
    private String password;
    private Date creationDate;
    private LocalTime creationTime;
    private int startingHour;
    private int startingMin;
    private int durationHour;
    private int durationMin;
    private int numberOfProblem;
    private int numberOfParticipants;
     @ManyToOne()
    private User contestCreater;
    @Enumerated(EnumType.STRING)
    private ContestStatus contestStatus;

 @OneToMany()
 private List<Problem> problemList;


    public static enum ContestStatus {

        CLOSED,
        ONGOING,
        UPCOMING
    }
}

