package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.Solution;
import com.dynamo.dynamo.model.SolutionComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolutionCommentRepository extends JpaRepository<SolutionComment , Long> {

    List<SolutionComment> findBySolution(Solution solution);
}
