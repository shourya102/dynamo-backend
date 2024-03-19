package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.CommunityPost;
import com.dynamo.dynamo.payload.request.CommunityPostRequest;

import java.util.List;

public interface CommunityPostService {

    CommunityPost addDiscussion(CommunityPostRequest communityPostRequest);
    CommunityPost getDiscussionBYId(Long id);

    List<CommunityPost> getAllDiscussion();
    String  toggleLikeOnCommunityPost(Long communityPost_id );
    String  toggleDislikeOnCommunityPost(Long communityPost_id );

    String  checkLikeOnCummunityByUser(Long communityPost_id );
    List<CommunityPost> getCommunityPostsByTag(String tag);
    List<CommunityPost> getCommunityPostsByTags(List<String> tags);
    List<CommunityPost> getCommunityPostsByTagsandTitle(String title ,List<String> tags);


}
