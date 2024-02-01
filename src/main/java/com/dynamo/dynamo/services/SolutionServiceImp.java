package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.Comment;
import com.dynamo.dynamo.model.Problem;
import com.dynamo.dynamo.model.Solution;
import com.dynamo.dynamo.model.User;
import com.dynamo.dynamo.repository.CommentRepository;
import com.dynamo.dynamo.repository.ProblemRepository;
import com.dynamo.dynamo.repository.SolutionRepository;
import com.dynamo.dynamo.repository.UserRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

//
//    @Override
//    public Solution saveSolution(Solution solution) {
//      return   solutionRepository.save(solution);
//    }
//

////    @Override
////    public void addCommentToSolution(Long solution_id, Comment comment, Long user_id) {
////        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution not found by  id -" +solution_id));
//////        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found by id -" +user_id));
//////        comment.setUser(user);
////        solution.getComments().add(comment);
////        solutionRepository.save(solution);
////
////    }
//@Override
//public void addCommentToSolution(Long solution_id, Comment comment) {
//    Solution solution = solutionRepository.findById(solution_id).orElseThrow(()->new RuntimeException("solution not found by  id -" +solution_id));
////        User user = userRepository.findById(user_id).orElseThrow(()->new RuntimeException("user not found by id -" +user_id));
////        comment.setUser(user);
//    solution.getComments().add(comment);
//    solutionRepository.save(solution);
//
//}
//

//
//    @Override
//    public List<Solution> getAllSolution() {
//        return solutionRepository.findAll();
//    }

















    @Override
    public Problem addSolution(Solution solution, Integer problem_id) {
        Problem questions = problemRepository.findById(Long.valueOf(problem_id)).orElseThrow(()->new RuntimeException("solution Not find" + problem_id));

        Solution solution1 = solutionRepository.save(solution);
        questions.setSolution(solution1);
        problemRepository.save(questions);


        return questions;
    }

    @Override
    public Solution addCommentToSolution(Comment comment, Long solution_id, Long user_id) {
        Solution solution = solutionRepository.findById(solution_id).orElseThrow(()-> new RuntimeException("solution not find" +solution_id +"idddddddddd"));
        User user = userRepository.findById(Long.valueOf(user_id)).orElseThrow(()-> new RuntimeException("solution not find" +solution_id +"idddddddddd"));
        comment.setUser(user);
        Comment comment1 =commentRepository.save(comment);
        solution.getCommentList().add(comment1);

        return solutionRepository.save(solution);
    }

    @Override
    public List<Solution> getAllSolution() {

        return solutionRepository.findAll();
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
