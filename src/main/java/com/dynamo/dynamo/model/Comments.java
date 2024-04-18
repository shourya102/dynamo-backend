package com.dynamo.dynamo.model;

import com.dynamo.dynamo.model.user.User;
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

    private  Integer likes = 0;
    private Integer dislikes = 0;

    @ManyToOne
    private User user;
}
