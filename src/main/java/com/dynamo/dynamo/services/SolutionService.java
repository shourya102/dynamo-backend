package com.dynamo.dynamo.services;

import com.dynamo.dynamo.model.Comment;
import com.dynamo.dynamo.model.Problem;
import com.dynamo.dynamo.model.Solution;

import java.util.List;

public interface SolutionService {

//    Solution saveSolution(Solution solution);
//
////    void addCommentToSolution(Long solution_id , Comment comment , Long user_id);
//void addCommentToSolution(Long solution_id , Comment comment );
//
//

//    List<Solution> getAllSolution();
    Solution getSolutionById(Long id);

    Problem addSolution(Solution solution , Integer problem_id);
    Solution addCommentToSolution(Comment comment , Long solution_id , Long user_id);

    Long increaseSolutionLike(Long solution_id);
    Long increaseCommentLike(Long comment_id);
    List<Solution> getAllSolution();
}
