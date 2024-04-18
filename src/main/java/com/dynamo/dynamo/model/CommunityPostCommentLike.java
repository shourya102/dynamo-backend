package com.dynamo.dynamo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CommunityPostCommentLike extends Likes{

    @ManyToOne
    @JoinColumn(name = "CommunityComment_id")
    private CommunityPostComments communityPostComments;


}
