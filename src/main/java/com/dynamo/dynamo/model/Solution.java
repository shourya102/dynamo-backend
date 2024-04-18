package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;


    private String bruteForceExplanation;

    private String BruteForceCode;

    private String optimalExplanation;

    private String optimalCode;
    private Integer likes;
    private Integer dislikes;

//    @OneToOne
//    private Problem problem;


    @OneToMany()
    private List<SolutionComment> commentList ;

    public  Solution(){
        likes =0;
        dislikes =0;
    }


}
