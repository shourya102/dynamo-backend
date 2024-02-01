package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment , Long> {
}
