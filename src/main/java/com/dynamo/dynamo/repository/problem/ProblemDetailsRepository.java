package com.dynamo.dynamo.repository.problem;

import com.dynamo.dynamo.model.problem.ProblemDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemDetailsRepository extends JpaRepository<ProblemDetails, Long> {
    Optional<ProblemDetails> findByProblemId(Long id);
}
