package com.dynamo.dynamo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class CommunityPostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    @ManyToMany(mappedBy = "communitTag" ,cascade = CascadeType.ALL  ,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<CommunityPost> communities = new HashSet<>();

}
