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
    private int startingHour;
    private int startingMin;
    private int durationHour;
    private int durationMin;
    private int  numberOfProblem;
    private int  numberOfParticipants;


}
