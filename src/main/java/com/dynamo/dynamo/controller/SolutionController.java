package com.dynamo.dynamo.controller;


import com.dynamo.dynamo.model.Problem;
import com.dynamo.dynamo.model.Solution;
import com.dynamo.dynamo.model.SolutionComment;
import com.dynamo.dynamo.payload.request.CommentsRequest;
import com.dynamo.dynamo.payload.request.SolutionRequest;
import com.dynamo.dynamo.services.SolutionService;
import com.dynamo.dynamo.services.SolutionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solution")
public class SolutionController {
    @Autowired
    SolutionService solutionService;
@Autowired
    SolutionServiceImp solutionServiceImp;
    @GetMapping("{solution_id}/getsolutionbyid")
    public ResponseEntity<Solution> getSolutionById(@PathVariable Long solution_id){
        Solution solution = solutionService.getSolutionById(solution_id);

        if(solution !=null){
            return    ResponseEntity.ok(solution);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{problem_id}/addsolution")
    ResponseEntity<String> addSolution(@RequestBody SolutionRequest solutionRequest , @PathVariable(name = "problem_id" ) Long problem_id){
       String res = solutionService.addSolution(solutionRequest  , problem_id);
       return ResponseEntity.ok(res);

    }
    @PostMapping("/{solution_id}/addcomment")
     ResponseEntity<String> addCommentToSolution(@RequestBody CommentsRequest commentsRequest , @PathVariable(name = "solution_id") Long solution_id ){
        String res = solutionService.addCommentToSolution(commentsRequest ,solution_id );
        return  ResponseEntity.ok(res);
     }
     @GetMapping("/getallsolution")
    ResponseEntity<List<Solution>> getAllSolution(){
        List<Solution> res = solutionService.getAllSolution();
        return ResponseEntity.ok(res);
     }
    @GetMapping("/{solution_id}/togglesolutionlike")
     ResponseEntity<String> toggleLikeOnsolution( @PathVariable(name = "solution_id") Long solution_id ){
        String res = solutionService.toggleLikeOnSolution(solution_id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{solution_id}/toggle-solution-dislike")
    ResponseEntity<String> toggleDisLikeOnsolution( @PathVariable(name = "solution_id") Long solution_id ){
        String res = solutionService.toggleDisLikeOnSolution(solution_id);
        return ResponseEntity.ok(res);
    }

//    @GetMapping("/{solution_id}/{user_id}/checksolutionlike")
//    ResponseEntity<Boolean> checkSolutionLikeByUser(@PathVariable(name = "solution_id") Long solution_id , @PathVariable(name = "user_id") Long user_id){
//        Boolean res = solutionService.checkLikeOnSolutionByUser(solution_id );
//        return ResponseEntity.ok(res);
//    }

    @GetMapping("/{comment_id}/togglecommentlike")
    ResponseEntity<String> toggleLikeOnComment( @PathVariable(name = "comment_id") Long comment_id ){
        String res = solutionService.toggleLikeOnComment(comment_id);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/{comment_id}/toggle-comment-dislike")
    ResponseEntity<String> toggleDisLikeOnComment( @PathVariable(name = "comment_id") Long comment_id ){
        String res = solutionService.toggleDisLikeOnComment(comment_id);
        return ResponseEntity.ok(res);
    }

@GetMapping("/{id}/get-comments-bysolution")
    ResponseEntity<List<SolutionComment>> getAllCommentBySolution(@PathVariable() Long id){
        return ResponseEntity.ok(solutionServiceImp.getCommentsByProblem(id));
}
//    @GetMapping("/{comment_id}/{user_id}/checkcommentlike")
//    ResponseEntity<Boolean> checkCommentLikeByUser(@PathVariable(name = "comment_id") Long comment_id , @PathVariable(name = "user_id") Long user_id){
//        Boolean res = solutionService.checkLikeOnCommentByUser(comment_id , user_id);
//        return ResponseEntity.ok(res);
//    }


}
