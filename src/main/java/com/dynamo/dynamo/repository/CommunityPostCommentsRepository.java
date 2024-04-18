package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.CommunityPost;
import com.dynamo.dynamo.model.CommunityPostComments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityPostCommentsRepository extends JpaRepository<CommunityPostComments , Long> {


    List<CommunityPostComments> findByCommunityPost(CommunityPost communityPost);
}
