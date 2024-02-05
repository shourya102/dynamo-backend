package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.auth.VerificationToken;
import com.dynamo.dynamo.model.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    public Optional<VerificationToken> findByUser(User user);

    public Optional<VerificationToken> findByToken(String token);

    @Transactional
    public void deleteByUser(User user);

    @Transactional
    public void deleteAllByExpirationDateBefore(Date date);
}
