package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.CommunityPostCommentLike;
import com.dynamo.dynamo.model.CommunityPostComments;
import com.dynamo.dynamo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityPostCommentLikeRepository extends JpaRepository<CommunityPostCommentLike , Long> {


    Optional<CommunityPostCommentLike> findByUserAndCommunityPostComments(User user , CommunityPostComments communityPostComments);
}
