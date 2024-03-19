package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.CommunityPost;
import com.dynamo.dynamo.model.CommunityPostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    List<CommunityPost> findByCommunitTag_Tag(String tag);
    List<CommunityPost> findByCommunitTag_TagIn( List<String> tags);
    List<CommunityPost> findByTitleContaining(String title);
//    List<CommunityPost> findByTitleContainingAndTags_TagIn(String titleKeyword, List<String> tags);
List<CommunityPost> findByTitleContainingAndCommunitTagIn(String titleKeyword, Set<CommunityPostTag> tags);
List<CommunityPost> findByCommunitTagIn(Set<CommunityPostTag> tags);
}
