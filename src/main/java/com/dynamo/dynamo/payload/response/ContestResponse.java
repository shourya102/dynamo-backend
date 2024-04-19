package com.dynamo.dynamo.payload.response;

import com.dynamo.dynamo.model.Contest;
import com.dynamo.dynamo.model.problem.Problem;
import com.dynamo.dynamo.model.user.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ContestResponse {
    private String name;
    private Date creationDate;
    private LocalTime creationTime;
    private int numberOfProblem;

    private int numberOfParticipants;
    private int registerParticipants;
    @ManyToOne()
    private User contestCreater;
    @Enumerated(EnumType.STRING)
    private Contest.ContestStatus contestStatus;

    @OneToMany()
    private List<Problem> problemList;
}
