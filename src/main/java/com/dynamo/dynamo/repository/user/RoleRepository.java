package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.user.ERole;
import com.dynamo.dynamo.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
