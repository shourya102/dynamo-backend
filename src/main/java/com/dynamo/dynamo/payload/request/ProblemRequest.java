package com.dynamo.dynamo.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class ProblemRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    private String difficulty;

    @NotBlank
    @Size(min = 10)
    private String problemDescription;

    @NotBlank
    private String returnType;

    @NotBlank
    private String methodName;

    private List<String> parameterNames;

    private List<String> parameterTypes;

    private Set<String> topics;
}
