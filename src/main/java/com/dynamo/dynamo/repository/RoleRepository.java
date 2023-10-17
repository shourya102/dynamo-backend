package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.ERole;
import com.dynamo.dynamo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
