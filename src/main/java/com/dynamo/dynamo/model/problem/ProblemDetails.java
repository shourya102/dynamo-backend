package com.dynamo.dynamo.model.problem;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProblemDetails {

    @Id
    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 10)
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank
    private String methodName;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private EType returnType;

    @OneToMany(mappedBy = "problemDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parameter> parameters;
    @OneToMany(mappedBy = "problemDetails", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TestCase> testCases = new ArrayList<>();
    @NotBlank
    private String testCode;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Problem problem;

    public ProblemDetails(String description, String methodName, EType returnType) {
        this.description = description;
        this.methodName = methodName;
        this.returnType = returnType;
    }
}


