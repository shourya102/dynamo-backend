package com.dynamo.dynamo.repository.problem;

import com.dynamo.dynamo.model.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Boolean existsByName(String name);
}
