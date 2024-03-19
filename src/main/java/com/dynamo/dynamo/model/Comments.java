package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @NotEmpty(message = "write a comment first ! please")
    private String comment;

    private  Long likes = 0L;

    @ManyToOne
    private User user;
}
