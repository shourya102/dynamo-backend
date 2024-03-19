package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.*;
import com.dynamo.dynamo.repository.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionServiceImp implements SolutionService{
    @Autowired
     SolutionRepository solutionRepository;
    @Autowired
     CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    SolutionLikeRepository solutionLikeRepository;
    @Autowired
    CommentLikesRepository commentLikesRepository;














    @Override
    public String addSolution(Solution solution, Long problem_id) {
        Problem questions = problemRepository.findById(problem_id).orElseThrow(()->new RuntimeException("solution Not find" + problem_id));
        solution.setProblem(questions);
        Solution solution1 = solutionRepository.save(solution);
        questions.setSolution(solution1);
        problemRepository.save(questions);


        return "Solution are added";
    }

    @Override
    public String addCommentToSolution(Comment comment, Long solution_id, Long user_id) {
        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()-> new RuntimeException("solution not find" +solution_id +"idddddddddd"));
        User user = userRepository.findById(Long.valueOf(user_id)).orElseThrow(()-> new RuntimeException("solution not find" +solution_id +"idddddddddd"));
        comment.setUser(user);
        Comment comment1 =commentRepository.save(comment);
        solution.getCommentList().add(comment1);

         solutionRepository.save(solution);
         return "Comment are added to the solutioon";
    }

    @Override
    public List<Solution> getAllSolution() {

        return solutionRepository.findAll();
    }

    @Override
    public String toggleLikeOnSolution(Long solution_id, Long user_id) {
        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution are not found" +solution_id));
        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));

        Optional<SolutionLikes> islikes = solutionLikeRepository.findByUserAndSolution(user ,solution);
        if(islikes.isPresent()){
            solution.setLikes(solution.getLikes()-1);
            solutionLikeRepository.delete(islikes.get());
            solutionRepository.save(solution);
            return "unlike on solution " +solution_id;

        }
        else {
            solution.setLikes(solution.getLikes()+1);
            Solution solution1= solutionRepository.save(solution);


            SolutionLikes solutionLikes = new SolutionLikes();
            solutionLikes.setSolution(solution1);
            solutionLikes.setUser(user);
            solutionLikeRepository.save(solutionLikes);
        }
        return "add like on solution"+solution_id;

    }

    @Override
    public Boolean checkLikeOnSolutionByUser(Long solution_id, Long user_id) {

        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution are not found" +solution_id));
        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));

        Optional<SolutionLikes> islikes = solutionLikeRepository.findByUserAndSolution(user ,solution);

        if (islikes.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public String toggleLikeOnComment(Long comment_id, Long user_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("comment are not found" +comment_id));
        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));

        Optional<CommentLikes> islikes = commentLikesRepository.findByUserAndComment(user ,comment);
        if(islikes.isPresent()){
            comment.setLikes(comment.getLikes()-1);
            commentLikesRepository.delete(islikes.get());
            commentRepository.save(comment);
            return "unlike on Comment  " +comment_id;

        }
        else {
            comment.setLikes(comment.getLikes()+1);
            Comment comment1= commentRepository.save(comment);


            CommentLikes commentLikes = new CommentLikes();
            commentLikes.setComment(comment1);
            commentLikes.setUser(user);
            commentLikesRepository.save(commentLikes);
        }
        return "add like on Comment "+comment_id;
    }

    @Override
    public Boolean checkLikeOnCommentByUser(Long comment_id, Long user_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("comment are not found" +comment_id));
        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found" +user_id));

        Optional<CommentLikes> islikes = commentLikesRepository.findByUserAndComment(user ,comment);

        if (islikes.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public Solution getSolutionById(Long id) {
        return solutionRepository.findById(id).orElse(null);
    }



    @Override
    public Long increaseSolutionLike(Long solution_id) {
        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution not found by id "+solution_id));
        solution.setLikes(solution.getLikes() + 1);
        solutionRepository.save(solution);
        return solution.getLikes();
    }

    @Override
    public Long increaseCommentLike(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(()->new RuntimeException("comment not found by id " +comment_id));
        comment.setLikes(comment.getLikes() + 1);
        commentRepository.save(comment);
        return comment.getLikes();
    }

}
