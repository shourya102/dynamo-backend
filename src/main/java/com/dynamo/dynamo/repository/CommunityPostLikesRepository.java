package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.CommunityPost;
import com.dynamo.dynamo.model.CommunityPostLikes;
import com.dynamo.dynamo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityPostLikesRepository extends JpaRepository<CommunityPostLikes, Long> {
//    Optional<CommunityPostLikes> findByUserAndCommunity(User user, CommunityPost communityPost);
    Optional<CommunityPostLikes> findByUserAndCommunityPost(User user , CommunityPost communityPost);
}
