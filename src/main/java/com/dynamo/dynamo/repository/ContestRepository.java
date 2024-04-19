package com.dynamo.dynamo.repository;


import com.dynamo.dynamo.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContestRepository extends JpaRepository<Contest , Long>  {

    Optional<Contest> findByContestId(Long contestId);
}
