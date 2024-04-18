package com.dynamo.dynamo.controller;

import com.dynamo.dynamo.model.Problem;
import com.dynamo.dynamo.payload.request.ProblemRequest;
import com.dynamo.dynamo.services.ProblemService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProblemController.class);
    @Autowired
    ProblemService problemService;

    @PostMapping("/create-problem")
    public ResponseEntity<?> createProblem(@Valid @RequestBody ProblemRequest problemRequest) {
        return problemService.saveProblem(problemRequest.getName(),
                problemRequest.getDifficulty(),
                problemRequest.getProblemDescription(),
                problemRequest.getReturnType(),
                problemRequest.getMethodName(),
                problemRequest.getParameterNames(),
                problemRequest.getParameterTypes(),
                problemRequest.getTopics());
    }
    @GetMapping("/getallproblem")
    public ResponseEntity<List<Problem>> getAllproblem(){
        List<Problem> res = problemService.getAllProblems();
        return ResponseEntity.ok(res);
    }


    @PostMapping("/{problem_id}/toggleproblemlike")
    ResponseEntity<String> toggleLikeOnProblem( @PathVariable(name = "problem_id") Long problem_id ){
        String res = problemService.toggleLikeOnProblem(problem_id );
        return ResponseEntity.ok(res);

    }
    @PostMapping("/{problem_id}/toggleproblemdislike")
    ResponseEntity<String> toggleDisLikeOnProblem( @PathVariable(name = "problem_id") Long problem_id ){
        String res = problemService.toggleDisLikeOnProblem(problem_id );
        return ResponseEntity.ok(res);

    }
    @GetMapping("/{problem_id}/checklike")
    ResponseEntity<String> isLikeOrDisLikeOrNormalByuser( @PathVariable(name = "problem_id") Long problem_id ){
        String res = problemService.isLikeOrDisLikeOrNormal(problem_id );
        return ResponseEntity.ok(res);

    }

}
