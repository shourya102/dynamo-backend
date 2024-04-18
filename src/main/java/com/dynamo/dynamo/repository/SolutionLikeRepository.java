package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.Solution;
import com.dynamo.dynamo.model.SolutionLikes;
import com.dynamo.dynamo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolutionLikeRepository extends JpaRepository<SolutionLikes , Long> {
    Optional<SolutionLikes> findByUserAndSolution(User user , Solution solution);
}
