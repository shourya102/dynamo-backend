package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.problem.Problem;
import com.dynamo.dynamo.model.Solution;
import com.dynamo.dynamo.payload.request.CommentsRequest;
import com.dynamo.dynamo.payload.request.SolutionRequest;

import java.util.List;

public interface SolutionService {


    Solution getSolutionById(Long id);

    String addSolution(SolutionRequest solutionRequest , Long problem_id);
    String addCommentToSolution(CommentsRequest commentsRequest , Long solution_id );

//    Long increaseSolutionLike(Long solution_id);
//    Long increaseCommentLike(Long comment_id);
    List<Solution> getAllSolution();

    String  toggleLikeOnSolution(Long solution_id );
    String  toggleDisLikeOnSolution(Long solution_id);

//    Boolean  checkLikeOnSolutionByUser(Long solution_id , Long user_id);
    String  toggleLikeOnComment(Long comment_id );
    String  toggleDisLikeOnComment(Long comment_id );

//    Boolean  checkLikeOnCommentByUser(Long comment_id , Long user_id);
}
