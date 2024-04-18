package com.dynamo.dynamo.repository.auth;

import com.dynamo.dynamo.model.auth.RefreshToken;
import com.dynamo.dynamo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    @Modifying
    int deleteByUser(User user);

    void deleteAllByExpiryDateBefore(Instant instant);

    Boolean existsByUser(User user);
}
