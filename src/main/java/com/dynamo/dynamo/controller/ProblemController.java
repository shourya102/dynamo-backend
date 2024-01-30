package com.dynamo.dynamo.controller;

import com.dynamo.dynamo.payload.request.ProblemRequest;
import com.dynamo.dynamo.services.ProblemService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
