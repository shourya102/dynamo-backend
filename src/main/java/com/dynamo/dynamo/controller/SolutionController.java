package com.dynamo.dynamo.controller;

import com.dynamo.dynamo.model.Comment;
import com.dynamo.dynamo.model.Problem;
import com.dynamo.dynamo.model.Solution;
import com.dynamo.dynamo.services.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solution")
public class SolutionController {
    @Autowired
    SolutionService solutionService;

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
    ResponseEntity<String> addSolution(@RequestBody Solution solution , @PathVariable(name = "problem_id" ) Long problem_id){
       String res = solutionService.addSolution(solution  , problem_id);
       return ResponseEntity.ok(res);

    }
    @PostMapping("/{solution_id}/{user_id}/addcomment")
     ResponseEntity<String> addCommentToSolution(@RequestBody Comment comment , @PathVariable(name = "solution_id") Long solution_id , @PathVariable(name = "user_id") Long user_id){
        String res = solutionService.addCommentToSolution(comment ,solution_id , user_id);
        return  ResponseEntity.ok(res);
     }
     @GetMapping("/getallsolution")
    ResponseEntity<List<Solution>> getAllSolution(){
        List<Solution> res = solutionService.getAllSolution();
        return ResponseEntity.ok(res);
     }
    @PostMapping("/{solution_id}/{user_id}/togglesolutionlike")
     ResponseEntity<String> toggleLikeOnsolution( @PathVariable(name = "solution_id") Long solution_id , @PathVariable(name = "user_id") Long user_id){
        String res = solutionService.toggleLikeOnSolution(solution_id,user_id);
        return ResponseEntity.ok(res);
    }
    @GetMapping("/{solution_id}/{user_id}/checksolutionlike")
    ResponseEntity<Boolean> checkSolutionLikeByUser(@PathVariable(name = "solution_id") Long solution_id , @PathVariable(name = "user_id") Long user_id){
        Boolean res = solutionService.checkLikeOnSolutionByUser(solution_id , user_id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{comment_id}/{user_id}/togglecommentlike")
    ResponseEntity<String> toggleLikeOnComment( @PathVariable(name = "comment_id") Long comment_id , @PathVariable(name = "user_id") Long user_id){
        String res = solutionService.toggleLikeOnComment(comment_id,user_id);
        return ResponseEntity.ok(res);
    }
    @GetMapping("/{comment_id}/{user_id}/checkcommentlike")
    ResponseEntity<Boolean> checkCommentLikeByUser(@PathVariable(name = "comment_id") Long comment_id , @PathVariable(name = "user_id") Long user_id){
        Boolean res = solutionService.checkLikeOnCommentByUser(comment_id , user_id);
        return ResponseEntity.ok(res);
    }


}
