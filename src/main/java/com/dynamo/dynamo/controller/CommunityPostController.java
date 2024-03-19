package com.dynamo.dynamo.controller;

import com.dynamo.dynamo.model.CommunityPost;
import com.dynamo.dynamo.model.CommunityPostTag;
import com.dynamo.dynamo.payload.request.CommunityPostRequest;
import com.dynamo.dynamo.repository.CommunityPostTagRepository;
import com.dynamo.dynamo.services.CommunityPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/community")
public class CommunityPostController {
    @Autowired
   private CommunityPostService communityPostService;
    @Autowired
    CommunityPostTagRepository communityPostTagRepository;

    @PostMapping("/postdiscussion")
    ResponseEntity<CommunityPost> postDiscussion(@Valid @RequestBody CommunityPostRequest communityPostRequest){
        CommunityPost communityPost = communityPostService.addDiscussion(communityPostRequest);

        return  ResponseEntity.ok(communityPost);
    }
    @GetMapping("/{id}/getdiscussionbyid")
    ResponseEntity<CommunityPost> getDiscussionById(@PathVariable(name = "id") Long id){
        CommunityPost communityPost = communityPostService.getDiscussionBYId(id);
        return  ResponseEntity.ok(communityPost);
    }
    @GetMapping("/getalldiscussion")
    ResponseEntity<List<CommunityPost>> getAllDiscussions(){
        List<CommunityPost> communityPostList = communityPostService.getAllDiscussion();
        return  ResponseEntity.ok(communityPostList);
    }
    @PostMapping("/{community_id}/likeoncommunitypost")
    ResponseEntity<String> toggleLikeOnCommunitypost(@PathVariable(name ="community_id" ) Long community_id ){
        String res = communityPostService.toggleLikeOnCommunityPost(community_id );

        return ResponseEntity.ok(res);
    }
    @PostMapping("/{community_id}/dislikeoncommunitypost")
    ResponseEntity<String> toggleDisLikeOnCommunitypost(@PathVariable(name ="community_id" ) Long community_id ){
        String res = communityPostService.toggleDislikeOnCommunityPost(community_id );

        return ResponseEntity.ok(res);
    }
    @GetMapping("/{community_id}/likeoncommunity")
    ResponseEntity<String> checkLikeOnCommunitypost(@PathVariable(name ="community_id" ) Long community_id ){
        String res = communityPostService.checkLikeOnCummunityByUser(community_id );
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{tag}/getcommunitybytag")
    ResponseEntity<List<CommunityPost>> getCommunityByTag(@PathVariable(name ="tag" ) String  tag ){
        List<CommunityPost> res=  communityPostService.getCommunityPostsByTag(tag);
        return ResponseEntity.ok(res);
    }
    @GetMapping("/{title}/by-tags")
    public List<CommunityPost> getCommunityPostsByTitleAndTags( @PathVariable(name = "title") String title ,@RequestParam("tags") List<String> tags) {
        return communityPostService.getCommunityPostsByTagsandTitle(title , tags);
    }
}
