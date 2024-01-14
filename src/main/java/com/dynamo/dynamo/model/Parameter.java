package com.dynamo.dynamo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Parameter {

    @Id
    private Long id;
    private String name;
    private Type type;
}
