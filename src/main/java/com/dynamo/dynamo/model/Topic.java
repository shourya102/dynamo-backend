package com.dynamo.dynamo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topic {

    @Id
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "topic")
    private Set<Problem> problem;

    public Topic(String name) {
        this.name = name;
    }
}
