package com.dynamo.dynamo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class SolutionCommentLike extends Likes{

    @ManyToOne
    @JoinColumn(name = "CommunityComment_id")
    private SolutionComment solutionComment;
}
