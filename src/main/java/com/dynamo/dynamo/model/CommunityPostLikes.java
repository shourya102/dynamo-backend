package com.dynamo.dynamo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CommunityPostLikes  extends Likes{


    @ManyToOne
    @JoinColumn(name = "Community_id")
    private CommunityPost communityPost;


}
