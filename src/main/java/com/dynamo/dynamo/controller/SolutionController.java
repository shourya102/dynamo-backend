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
//
//    @PostMapping("/savesolution")
//    public ResponseEntity<String> insertSolutionWithOutComment(@RequestBody Solution solution){
////        solution.setComments(null);
//        Solution savedSolution = solutionService.saveSolution(solution);
//        return ResponseEntity.ok("Solution are inserted by Id :-" +savedSolution.getId());
//    }
//
////    @PostMapping("/{solution_id}/insertcomment/{user_id}")
////    public ResponseEntity<String> addCommentToSolution(@PathVariable("solution_id") Long solution_id , @RequestBody Comment comment , @PathVariable("user_id") Long user_id){
////        solutionService.addCommentToSolution(solution_id ,comment ,user_id);
////
////        return ResponseEntity.ok("comment add to the solution successfully by the id " +user_id);
////
////    }
//@PostMapping("/insertcomment/{solution_id}")
//public ResponseEntity<String> addCommentToSolution(@PathVariable("solution_id") Long solution_id , @RequestBody Comment comment ){
//    solutionService.addCommentToSolution(solution_id ,comment );
//
//    return ResponseEntity.ok("comment add to the solution successfully by the id " + solution_id);
//
//}
//
//

//    @GetMapping("/getallsolution")
//    public ResponseEntity<List<Solution>>  getAllSolution(){
//        return ResponseEntity.ok(solutionService.getAllSolution());
//    }
    public ResponseEntity<Solution> getSolutionById(@PathVariable Long solution_id){
        Solution solution = solutionService.getSolutionById(solution_id);

        if(solution !=null){
            return    ResponseEntity.ok(solution);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }



}
