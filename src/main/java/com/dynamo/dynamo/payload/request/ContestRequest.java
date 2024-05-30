package com.dynamo.dynamo.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
public class ContestRequest {
    private List<String> problemType;
    private String password;
    private String startingTime;
    private String endingTime;
    private int  numberOfProblem;
    private int  numberOfParticipants;

}
