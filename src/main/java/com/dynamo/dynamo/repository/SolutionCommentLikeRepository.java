package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.SolutionComment;
import com.dynamo.dynamo.model.SolutionCommentLike;
import com.dynamo.dynamo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolutionCommentLikeRepository extends JpaRepository<SolutionCommentLike , Long> {

    Optional<SolutionCommentLike> findByUserAndSolutionComment(User user , SolutionComment solutionComment);

}
