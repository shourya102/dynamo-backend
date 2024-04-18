package com.dynamo.dynamo.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolutionRequest {


    private String bruteForceExplanation;

    private String BruteForceCode;

    private String optimalExplanation;

    private String optimalCode;
}
