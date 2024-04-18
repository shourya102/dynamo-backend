package com.dynamo.dynamo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title ;
    @ManyToMany()
//    @JsonManagedReference
    private Set<CommunityPostTag> communitTag = new HashSet<>();
    private String description;
    private Integer likes;
    private Integer dislikes;

    public CommunityPost() {
        this.likes =0;
        this.dislikes=0;
    }
}
