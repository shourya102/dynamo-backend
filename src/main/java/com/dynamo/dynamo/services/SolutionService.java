package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.Comment;
import com.dynamo.dynamo.model.Problem;
import com.dynamo.dynamo.model.Solution;

import java.util.List;

public interface SolutionService {


    Solution getSolutionById(Long id);

    String addSolution(Solution solution , Long problem_id);
    String addCommentToSolution(Comment comment , Long solution_id , Long user_id);

    Long increaseSolutionLike(Long solution_id);
    Long increaseCommentLike(Long comment_id);
    List<Solution> getAllSolution();

    String  toggleLikeOnSolution(Long solution_id , Long user_id);
    Boolean  checkLikeOnSolutionByUser(Long solution_id , Long user_id);
    String  toggleLikeOnComment(Long comment_id , Long user_id);
    Boolean  checkLikeOnCommentByUser(Long comment_id , Long user_id);
}
