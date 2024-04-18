package com.dynamo.dynamo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CommunityPostComments extends Comments{
    @ManyToOne
    @JoinColumn(name = "Community_id")
    private CommunityPost communityPost;



}
