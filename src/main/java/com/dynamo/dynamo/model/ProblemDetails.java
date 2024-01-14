package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank
    private Type returnType;

    @OneToMany(mappedBy = "problemDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parameter> parameters;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Problem problem;

    public ProblemDetails(String description, String methodName, Type returnType) {
        this.description = description;
        this.methodName = methodName;
        this.returnType = returnType;
    }
}


