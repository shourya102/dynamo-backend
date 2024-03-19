package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.*;
import com.dynamo.dynamo.payload.request.CommentsRequest;
import com.dynamo.dynamo.payload.request.CommunityPostRequest;
import com.dynamo.dynamo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommunityPostServiceImpl implements CommunityPostService {
    @Autowired
    private CommunityPostRepository communityPostRepository;
    @Autowired
    private CommunityPostTagRepository communityTagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommunityPostLikesRepository communityPostLikesRepository;
    @Autowired
    CommunityPostCommentsRepository communityPostCommentsRepository;
    @Autowired
    CommunityPostCommentLikeRepository communityPostCommentLikeRepository;
    @Override
    public CommunityPost addDiscussion(CommunityPostRequest communityPostRequest) {
        CommunityPost communityPost = new CommunityPost();
        communityPost.setTitle(communityPostRequest.getTitle());
        communityPost.setDescription(communityPostRequest.getDescription());
        List<String> tags = communityPostRequest.getTags();
        for(String tag : tags){
            Optional<CommunityPostTag> req = communityTagRepository.findByTag(tag);
            if(req.isPresent()){
                communityPost.getCommunitTag().add(req.get());
            }
            else {
                CommunityPostTag communityPostTag = new CommunityPostTag();
                communityPostTag.setTag(tag);
                CommunityPostTag communityPostTag1 = communityTagRepository.save(communityPostTag);
                communityPost.getCommunitTag().add(communityPostTag1);
            }

        }
      CommunityPost communityPost1 =  communityPostRepository.save(communityPost);
        return communityPost1;
    }

    @Override
    public CommunityPost getDiscussionBYId(Long id) {
        CommunityPost communityPost = communityPostRepository.findById(id).orElseThrow(()->new RuntimeException("Discussion not found" + id));
        return communityPost;
    }

    @Override
    public List<CommunityPost> getAllDiscussion() {
        return communityPostRepository.findAll();
    }

    @Override
    public String toggleLikeOnCommunityPost(Long communityPost_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommunityPost communityPost = communityPostRepository.findById(communityPost_id).orElseThrow(()->new RuntimeException("Community Post are not found" +communityPost_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));

        Optional<CommunityPostLikes> islikes = communityPostLikesRepository.findByUserAndCommunityPost(user ,communityPost);
        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)){
            communityPost.setLikes(communityPost.getLikes()-1);
            communityPostLikesRepository.delete(islikes.get());
            communityPostRepository.save(communityPost);
            return " remove like on Community Post id "+communityPost_id +" by user name " +userDetails.getUsername();
        }
        else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)) {
            communityPost.setLikes(communityPost.getLikes()+1);
            communityPost.setDislikes(communityPost.getDislikes()-1);
            communityPostRepository.save(communityPost);
            islikes.get().setLikeStatus(LikeStatus.LIKE);
            communityPostLikesRepository.save(islikes.get());
        }
        else {
            communityPost.setLikes(communityPost.getLikes()+1);
            CommunityPost communityPost1= communityPostRepository.save(communityPost);
            CommunityPostLikes communityPostLikes = new CommunityPostLikes();
            communityPostLikes.setCommunityPost(communityPost1);
            communityPostLikes.setUser(user);
            communityPostLikes.setLikeStatus(LikeStatus.LIKE);
            communityPostLikesRepository.save(communityPostLikes);
        }
        return "like on Community Post id "+communityPost_id +" by user name " +userDetails.getUsername();
    }

    @Override
    public String toggleDislikeOnCommunityPost(Long communityPost_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommunityPost communityPost = communityPostRepository.findById(communityPost_id).orElseThrow(()->new RuntimeException("Community Post are not found" +communityPost_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
        Optional<CommunityPostLikes> islikes = communityPostLikesRepository.findByUserAndCommunityPost(user ,communityPost);

        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)){
            communityPost.setDislikes(communityPost.getDislikes()-1);
            communityPostLikesRepository.delete(islikes.get());
            communityPostRepository.save(communityPost);
            return "remove dislike on Community Post id "+communityPost_id +" by user name " +userDetails.getUsername();

        } else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)) {
            communityPost.setLikes(communityPost.getLikes()-1);
            communityPost.setDislikes(communityPost.getDislikes()+1);
            communityPostRepository.save(communityPost);
            islikes.get().setLikeStatus(LikeStatus.DISLIKE);
            communityPostLikesRepository.save(islikes.get());
        } else {
            communityPost.setDislikes(communityPost.getDislikes()+1);
            CommunityPost communityPost1= communityPostRepository.save(communityPost);
            CommunityPostLikes communityPostLikes = new CommunityPostLikes();
            communityPostLikes.setCommunityPost(communityPost1);
            communityPostLikes.setUser(user);
            communityPostLikes.setLikeStatus(LikeStatus.DISLIKE);
            communityPostLikesRepository.save(communityPostLikes);
        }
        return "Dislike on Community Post id "+communityPost_id +" by user name " +userDetails.getUsername();
    }

    @Override
    public String checkLikeOnCummunityByUser(Long communityPost_id) {
        CommunityPost communityPost = communityPostRepository.findById(communityPost_id).orElseThrow(()->new RuntimeException("Community Post are not found" +communityPost_id));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
        Optional<CommunityPostLikes> islikes = communityPostLikesRepository.findByUserAndCommunityPost(user ,communityPost);
        if(islikes.isPresent()){
            return islikes.get().getLikeStatus().toString();
        }
        return "NORMAL";
    }

    @Override
    public List<CommunityPost> getCommunityPostsByTag(String tag) {
        List<CommunityPost> communityPosts = communityPostRepository.findByCommunitTag_Tag(tag);
        return communityPosts;
    }


    @Override
    public List<CommunityPost> getCommunityPostsByTags(List<String> tags) {
        return communityPostRepository.findByCommunitTag_TagIn(tags);
    }
//----searching-------------------
    @Override
    public List<CommunityPost> getCommunityPostsByTagsandTitle(String title, List<String> tags) {
        Set<CommunityPostTag> tag = new HashSet<>();
        for(String t :tags){
            Optional<CommunityPostTag> tag1 = communityTagRepository.findByTag(t);
            if (tag1.isPresent()){
                tag.add(tag1.get());
            }
        }
        List<CommunityPost> res = communityPostRepository.findByTitleContainingAndCommunitTagIn(title,tag);


        return res;
        
    }

    public String postCommentOnCommunityPost(CommentsRequest commentsRequest, Long communityId){
        CommunityPost communityPost = communityPostRepository.findById(communityId).orElseThrow(()->new RuntimeException("community Post not found "));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
        CommunityPostComments communityPostComments = new CommunityPostComments();
        communityPostComments.setComment(commentsRequest.getComments());
        communityPostComments.setUser(user);
        communityPostComments.setCommunityPost(communityPost);
        communityPostCommentsRepository.save(communityPostComments);

        return "Comment Successfully on Community post  !";
    }

    public String toggleLikeOnCommunityPostComment(Long communityPostCommentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommunityPostComments communityPostComments = communityPostCommentsRepository.findById(communityPostCommentId).orElseThrow(()->new RuntimeException("Community Post are not found" +communityPostCommentId));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));

        Optional<CommunityPostCommentLike> islikes = communityPostCommentLikeRepository.findByUserAndCommunityPostComments(user ,communityPostComments);
        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)){
            communityPostComments.setLikes(communityPostComments.getLikes()-1);
            communityPostCommentLikeRepository.delete(islikes.get());
            communityPostCommentsRepository.save(communityPostComments);
            return " remove like on Community Post id "+communityPostCommentId +" by user name " +userDetails.getUsername();
        }
        else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)) {
            communityPostComments.setLikes(communityPostComments.getLikes()+1);
            communityPostComments.setDislikes(communityPostComments.getDislikes()-1);
            communityPostCommentsRepository.save(communityPostComments);
            islikes.get().setLikeStatus(LikeStatus.LIKE);
            communityPostCommentLikeRepository.save(islikes.get());
        }
        else {
            communityPostComments.setLikes(communityPostComments.getLikes()+1);
            CommunityPostComments communityPostComments1  = communityPostCommentsRepository.save(communityPostComments);
            CommunityPostCommentLike communityPostCommentLike = new CommunityPostCommentLike();
            communityPostCommentLike.setCommunityPostComments(communityPostComments1);
            communityPostCommentLike.setUser(user);
            communityPostCommentLike.setLikeStatus(LikeStatus.LIKE);
            communityPostCommentLikeRepository.save(communityPostCommentLike);
        }
        return "like on Community Post id "+communityPostCommentId +" by user name " +userDetails.getUsername();

    }


    public String toggleDislikeOnCommunityPostComments(Long communityPostCommentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CommunityPostComments communityPostComments = communityPostCommentsRepository.findById(communityPostCommentId).orElseThrow(()->new RuntimeException("Community Post are not found" +communityPostCommentId));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));

        Optional<CommunityPostCommentLike> islikes = communityPostCommentLikeRepository.findByUserAndCommunityPostComments(user ,communityPostComments);

        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)){
            communityPostComments.setDislikes(communityPostComments.getDislikes()-1);
            communityPostCommentLikeRepository.delete(islikes.get());
            communityPostCommentsRepository.save(communityPostComments);
            return "remove dislike on Community Post id "+communityPostCommentId +" by user name " +userDetails.getUsername();

        } else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)) {
            communityPostComments.setLikes(communityPostComments.getLikes()-1);
            communityPostComments.setDislikes(communityPostComments.getDislikes()+1);
            communityPostCommentsRepository.save(communityPostComments);
            islikes.get().setLikeStatus(LikeStatus.DISLIKE);
            communityPostCommentLikeRepository.save(islikes.get());
        } else {
            communityPostComments.setDislikes(communityPostComments.getDislikes()+1);
            CommunityPostComments communityPostComments1= communityPostCommentsRepository.save(communityPostComments);
            CommunityPostCommentLike communityPostCommentLike = new CommunityPostCommentLike();
            communityPostCommentLike.setCommunityPostComments(communityPostComments1);
            communityPostCommentLike.setUser(user);
            communityPostCommentLike.setLikeStatus(LikeStatus.DISLIKE);
            communityPostCommentLikeRepository.save(communityPostCommentLike);
        }
        return "Dislike on Community Post id "+communityPostCommentId +" by user name " +userDetails.getUsername();
    }


    public String deleteCommunityPost(Long communityPostId){
        CommunityPost communityPost = communityPostRepository.findById(communityPostId).orElseThrow(()->new RuntimeException("Community Post are not found" +communityPostId));

        communityPostRepository.delete(communityPost);

        return "delete Successfully !";
    }
    public String deleteCommunityPostComment(Long communityPostCommentId){
        CommunityPostComments communityPostComments = communityPostCommentsRepository.findById(communityPostCommentId).orElseThrow(()->new RuntimeException("Community Post are not found" +communityPostCommentId));


        communityPostCommentsRepository.delete(communityPostComments);

        return "delete Successfully !";
    }


}
