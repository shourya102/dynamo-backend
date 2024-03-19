package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;


    private String bruteForceExplanation;

    private String BruteForceCode;

    private String optimalExplanation;

    private String optimalCode;

    private Long likes ;
    @OneToOne
    private Problem problem;
    @OneToMany()
    private List<Comment> commentList ;



}
