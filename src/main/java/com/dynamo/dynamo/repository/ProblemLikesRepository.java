package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.problem.Problem;
import com.dynamo.dynamo.model.ProblemLikes;
import com.dynamo.dynamo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemLikesRepository extends JpaRepository<ProblemLikes , Long> {
    Optional<ProblemLikes> findByUserAndProblem(User user , Problem problem);
}
