package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.Comment;
import com.dynamo.dynamo.model.CommentLikes;
import com.dynamo.dynamo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLikes , Long> {
    Optional<CommentLikes> findByUserAndComment(User user , Comment comment);
}
