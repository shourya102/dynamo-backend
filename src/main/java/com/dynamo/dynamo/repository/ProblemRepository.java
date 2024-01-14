package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Boolean existsByName(String name);
}
