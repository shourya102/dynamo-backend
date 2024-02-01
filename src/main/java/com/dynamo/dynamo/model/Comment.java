package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotEmpty(message = "write a comment first ! please")
    private String comment;

    private  Long likes;

    @ManyToOne
    private User user;

}
