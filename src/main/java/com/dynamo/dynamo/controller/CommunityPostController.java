package com.dynamo.dynamo.controller;

import com.dynamo.dynamo.model.CommunityPost;
import com.dynamo.dynamo.model.CommunityPostComments;
import com.dynamo.dynamo.model.CommunityPostTag;
import com.dynamo.dynamo.model.SolutionComment;
import com.dynamo.dynamo.payload.request.CommentsRequest;
import com.dynamo.dynamo.payload.request.CommunityPostRequest;
import com.dynamo.dynamo.payload.response.CommunityPostCommentResponse;
import com.dynamo.dynamo.payload.response.CommunityPostResponse;
import com.dynamo.dynamo.payload.response.CommunityPostsListResponse;
import com.dynamo.dynamo.repository.CommunityPostTagRepository;
import com.dynamo.dynamo.services.CommunityPostService;
import com.dynamo.dynamo.services.CommunityPostServiceImpl;
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
    @Autowired
    CommunityPostServiceImpl communityPostServiceImpl;

    @PostMapping("/postdiscussion")
    ResponseEntity<CommunityPost> postDiscussion(@Valid @RequestBody CommunityPostRequest communityPostRequest){
        CommunityPost communityPost = communityPostService.addDiscussion(communityPostRequest);

        return  ResponseEntity.ok(communityPost);
    }
    @GetMapping("/{id}/getdiscussionbyid")
    ResponseEntity<CommunityPostResponse> getDiscussionById(@PathVariable(name = "id") Long id){
        CommunityPostResponse communityPostResponse = communityPostServiceImpl.getCommunityPostById(id);
        return  ResponseEntity.ok(communityPostResponse);
    }
    @GetMapping("/getalldiscussion")
    ResponseEntity<List<CommunityPostsListResponse>> getAllDiscussions(){
        List<CommunityPostsListResponse> communityPostList = communityPostServiceImpl.getAllCommunityPost();
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


    @PostMapping("/post-comment/{id}")
    public ResponseEntity<String> CommentOnCommunity(@RequestBody CommentsRequest commentsRequest , @PathVariable(name = "id") Long id){
      String  res =   communityPostServiceImpl.postCommentOnCommunityPost(commentsRequest , id);
      return  ResponseEntity.ok(res);
    }

    @GetMapping("/like-on-CommunityComment/{id}")
    public ResponseEntity<String> ToggleLikeOnCommunityComment(@PathVariable(name =  "id") Long id){
     return ResponseEntity.ok( communityPostServiceImpl.toggleLikeOnCommunityPostComment(id)) ;
    }

    @GetMapping("/dislike-on-CommunityComment/{id}")
    public ResponseEntity<String> ToggleDisLikeOnCommunityComment(@PathVariable(name =  "id") Long id){
        return ResponseEntity.ok( communityPostServiceImpl.toggleDislikeOnCommunityPostComments(id)) ;
    }

    @GetMapping("/{id}/get-comments-by-communityPostId")
    ResponseEntity<List<CommunityPostCommentResponse>> getAllCommentByProblem(@PathVariable() Long id){
        return ResponseEntity.ok(communityPostServiceImpl.getCommunityPostComment(id));
    }
}
