package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.*;
import com.dynamo.dynamo.model.problem.Problem;
import com.dynamo.dynamo.model.user.User;
import com.dynamo.dynamo.payload.request.CommentsRequest;
import com.dynamo.dynamo.payload.request.SolutionRequest;
import com.dynamo.dynamo.repository.*;
import com.dynamo.dynamo.repository.problem.ProblemRepository;
import com.dynamo.dynamo.repository.user.UserRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionServiceImp implements SolutionService{
    @Autowired
     SolutionRepository solutionRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    SolutionLikeRepository solutionLikeRepository;
    @Autowired
    SolutionCommentRepository solutionCommentRepository;
    @Autowired
    SolutionCommentLikeRepository solutionCommentLikeRepository;


    @Override
    public Solution getSolutionById(Long id) {
        return null;
    }

    @Override
    public String addSolution(SolutionRequest solutionRequest, Long problem_id) {

        Problem questions = problemRepository.findById(problem_id).orElseThrow(()->new RuntimeException("solution Not find" + problem_id));
        if(questions.getSolution()!=null){
            questions.getSolution().setBruteForceExplanation(solutionRequest.getBruteForceExplanation());
            questions.getSolution().setBruteForceCode(solutionRequest.getBruteForceCode());
            questions.getSolution().setOptimalExplanation(solutionRequest.getOptimalExplanation());
            questions.getSolution().setOptimalCode(solutionRequest.getOptimalCode());
            problemRepository.save(questions);
            return "solution update successfully";
        }
        Solution solution = new Solution();
        solution.setBruteForceExplanation(solutionRequest.getBruteForceExplanation());
        solution.setBruteForceCode(solutionRequest.getBruteForceCode());
        solution.setOptimalExplanation(solutionRequest.getOptimalExplanation());
        solution.setOptimalCode(solutionRequest.getOptimalCode());
        Solution solution1 = solutionRepository.save(solution);
        questions.setSolution(solution1);
        problemRepository.save(questions);

        return "Solution are added";
    }

    @Override
    public String addCommentToSolution(CommentsRequest commentsRequest, Long solution_id) {
        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution  not found "));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
        SolutionComment solutionComment = new SolutionComment();
        solutionComment.setComment(commentsRequest.getComments());
        solutionComment.setUser(user);
        solutionComment.setSolution(solution);
        solutionCommentRepository.save(solutionComment);

        return "Comment Successfully on Community post  !";
    }

    @Override
    public List<Solution> getAllSolution() {
        return solutionRepository.findAll();
    }

    @Override
    public String toggleLikeOnSolution(Long solution_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("Solution are not found  " +solution_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));

        Optional<SolutionLikes> islikes = solutionLikeRepository.findByUserAndSolution(user ,solution);
        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)){
            solution.setLikes(solution.getLikes()-1);
            solutionLikeRepository.delete(islikes.get());
            solutionRepository.save(solution);
            return " remove like on Solution id "+solution_id +" by user name " +userDetails.getUsername();
        }
        else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)) {
            solution.setLikes(solution.getLikes()+1);
            solution.setDislikes(solution.getDislikes()-1);
            solutionRepository.save(solution);
            islikes.get().setLikeStatus(LikeStatus.LIKE);
            solutionLikeRepository.save(islikes.get());
        }
        else {
            solution.setLikes(solution.getLikes()+1);
            Solution solution1= solutionRepository.save(solution);
            SolutionLikes solutionLikes = new SolutionLikes();
            solutionLikes.setSolution(solution1);
            solutionLikes.setUser(user);
            solutionLikes.setLikeStatus(LikeStatus.LIKE);
            solutionLikeRepository.save(solutionLikes);
        }
        return "like on Solution  id  "+solution_id +" by user name " +userDetails.getUsername();
    }

    @Override
    public String toggleDisLikeOnSolution(Long solution_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("Solution are not found" +solution_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));
        Optional<SolutionLikes> islikes = solutionLikeRepository.findByUserAndSolution(user ,solution);

        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)){
            solution.setDislikes(solution.getDislikes()-1);
            solutionLikeRepository.delete(islikes.get());
            solutionRepository.save(solution);
            return "remove dislike on Solution  id "+solution_id +" by user name " +userDetails.getUsername();

        } else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)) {
            solution.setLikes(solution.getLikes()-1);
            solution.setDislikes(solution.getDislikes()+1);
            solutionRepository.save(solution);
            islikes.get().setLikeStatus(LikeStatus.DISLIKE);
            solutionLikeRepository.save(islikes.get());
        } else {
            solution.setDislikes(solution.getDislikes()+1);
            Solution solution1= solutionRepository.save(solution);
            SolutionLikes solutionLikes = new SolutionLikes();
            solutionLikes.setSolution(solution1);
            solutionLikes.setUser(user);
            solutionLikes.setLikeStatus(LikeStatus.DISLIKE);
            solutionLikeRepository.save(solutionLikes);
        }
        return "Dislike on Solution  id "+solution_id +" by user name " +userDetails.getUsername();
    }

    @Override
    public String toggleLikeOnComment(Long comment_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SolutionComment solutionComment = solutionCommentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("Solution Comment  are not found" +comment_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));

        Optional<SolutionCommentLike> islikes = solutionCommentLikeRepository.findByUserAndSolutionComment(user ,solutionComment);
        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)){
            solutionComment.setLikes(solutionComment.getLikes()-1);
            solutionCommentLikeRepository.delete(islikes.get());
            solutionCommentRepository.save(solutionComment);
            return " remove like on Solution  Comment id "+comment_id +" by user name " +userDetails.getUsername();
        }
        else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)) {
            solutionComment.setLikes(solutionComment.getLikes()+1);
            solutionComment.setDislikes(solutionComment.getDislikes()-1);
            solutionCommentRepository.save(solutionComment);
            islikes.get().setLikeStatus(LikeStatus.LIKE);
            solutionCommentLikeRepository.save(islikes.get());
        }
        else {
            solutionComment.setLikes(solutionComment.getLikes()+1);
            SolutionComment solutionComment1  = solutionCommentRepository.save(solutionComment);
            SolutionCommentLike solutionCommentLike = new SolutionCommentLike();
            solutionCommentLike.setSolutionComment(solutionComment1);
            solutionCommentLike.setUser(user);
            solutionCommentLike.setLikeStatus(LikeStatus.LIKE);
            solutionCommentLikeRepository.save(solutionCommentLike);
        }
        return "like on Solution Comment id "+comment_id +" by user name " +userDetails.getUsername();
    }

    @Override
    public String toggleDisLikeOnComment(Long comment_id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SolutionComment solutionComment = solutionCommentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("Solution Comment  are not found" + comment_id));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new RuntimeException("user not found" +userDetails.getUsername()));

        Optional<SolutionCommentLike> islikes = solutionCommentLikeRepository.findByUserAndSolutionComment(user ,solutionComment);

        if(islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.DISLIKE)){
            solutionComment.setDislikes(solutionComment.getDislikes()-1);
            solutionCommentLikeRepository.delete(islikes.get());
            solutionCommentRepository.save(solutionComment);
            return "remove dislike on Solution Comment id "+comment_id +" by user name " +userDetails.getUsername();

        } else if (islikes.isPresent() && islikes.get().getLikeStatus().equals(LikeStatus.LIKE)) {
            solutionComment.setLikes(solutionComment.getLikes()-1);
            solutionComment.setDislikes(solutionComment.getDislikes()+1);
            solutionCommentRepository.save(solutionComment);
            islikes.get().setLikeStatus(LikeStatus.DISLIKE);
            solutionCommentLikeRepository.save(islikes.get());
        } else {
            solutionComment.setDislikes(solutionComment.getDislikes()+1);
            SolutionComment solutionComment1= solutionCommentRepository.save(solutionComment);
            SolutionCommentLike solutionCommentLike = new SolutionCommentLike();
            solutionCommentLike.setSolutionComment(solutionComment);
            solutionCommentLike.setUser(user);
            solutionCommentLike.setLikeStatus(LikeStatus.DISLIKE);
            solutionCommentLikeRepository.save(solutionCommentLike);
        }
        return "Dislike on Solution Comment id  "+comment_id +" by user name  " +userDetails.getUsername();
    }


//
//    @Override
//    public String addCommentToSolution(Comment comment, Long solution_id, Long user_id) {
//        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()-> new RuntimeException("solution not find" +solution_id +"idddddddddd"));
//        User user = userRepository.findById(Long.valueOf(user_id)).orElseThrow(()-> new RuntimeException("solution not find" +solution_id +"idddddddddd"));
//        comment.setUser(user);
//        Comment comment1 =commentRepository.save(comment);
//        solution.getCommentList().add(comment1);
//
//         solutionRepository.save(solution);
//         return "Comment are added to the solutioon";
//    }
//
//    @Override
//    public List<Solution> getAllSolution() {
//
//        return solutionRepository.findAll();
//    }
//
//    @Override
//    public String toggleLikeOnSolution(Long solution_id, Long user_id) {
//        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution are not found" +solution_id));
//        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));
//
//        Optional<SolutionLikes> islikes = solutionLikeRepository.findByUserAndSolution(user ,solution);
//        if(islikes.isPresent()){
//            solution.setLikes(solution.getLikes()-1);
//            solutionLikeRepository.delete(islikes.get());
//            solutionRepository.save(solution);
//            return "unlike on solution " +solution_id;
//
//        }
//        else {
//            solution.setLikes(solution.getLikes()+1);
//            Solution solution1= solutionRepository.save(solution);
//
//
//            SolutionLikes solutionLikes = new SolutionLikes();
//            solutionLikes.setSolution(solution1);
//            solutionLikes.setUser(user);
//            solutionLikeRepository.save(solutionLikes);
//        }
//        return "add like on solution"+solution_id;
//
//    }
//
//    @Override
//    public Boolean checkLikeOnSolutionByUser(Long solution_id, Long user_id) {
//
//        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution are not found" +solution_id));
//        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));
//
//        Optional<SolutionLikes> islikes = solutionLikeRepository.findByUserAndSolution(user ,solution);
//
//        if (islikes.isPresent()){
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public String toggleLikeOnComment(Long comment_id, Long user_id) {
//        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("comment are not found" +comment_id));
//        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));
//
//        Optional<CommentLikes> islikes = commentLikesRepository.findByUserAndComment(user ,comment);
//        if(islikes.isPresent()){
//            comment.setLikes(comment.getLikes()-1);
//            commentLikesRepository.delete(islikes.get());
//            commentRepository.save(comment);
//            return "unlike on Comment  " +comment_id;
//
//        }
//        else {
//            comment.setLikes(comment.getLikes()+1);
//            Comment comment1= commentRepository.save(comment);
//
//
//            CommentLikes commentLikes = new CommentLikes();
//            commentLikes.setComment(comment1);
//            commentLikes.setUser(user);
//            commentLikesRepository.save(commentLikes);
//        }
//        return "add like on Comment "+comment_id;
//    }
//
//    @Override
//    public Boolean checkLikeOnCommentByUser(Long comment_id, Long user_id) {
//        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("comment are not found" +comment_id));
//        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));
//
//        Optional<CommentLikes> islikes = commentLikesRepository.findByUserAndComment(user ,comment);
//
//        if (islikes.isPresent()){
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public Solution getSolutionById(Long id) {
//        return solutionRepository.findById(id).orElse(null);
//    }
//
//
//
//    @Override
//    public Long increaseSolutionLike(Long solution_id) {
//        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution not found by id "+solution_id));
//        solution.setLikes(solution.getLikes() + 1);
//        solutionRepository.save(solution);
//        return solution.getLikes();
//    }
//
//    @Override
//    public Long increaseCommentLike(Long comment_id) {
//        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("comment not found by id " +comment_id));
//        comment.setLikes(comment.getLikes() + 1);
//        commentRepository.save(comment);
//        return comment.getLikes();
//    }



    public List<SolutionComment> getCommentsByProblem(Long id){
        Solution solution = solutionRepository.findById(id).orElseThrow(()->new RuntimeException("Solution not found"));

        return solutionCommentRepository.findBySolution(solution);
    }

}
