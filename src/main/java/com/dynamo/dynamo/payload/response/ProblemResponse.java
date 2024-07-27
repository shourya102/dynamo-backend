package com.dynamo.dynamo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponse {
    Long id;
    String name;
    String difficulty;
    String numberOfAttempts;
    String successfulAttempts;
    String description;
}
