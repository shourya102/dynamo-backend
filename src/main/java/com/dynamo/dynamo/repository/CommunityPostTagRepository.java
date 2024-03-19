package com.dynamo.dynamo.repository;

import com.dynamo.dynamo.model.CommunityPostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityPostTagRepository extends JpaRepository<CommunityPostTag,Long> {

        Optional<CommunityPostTag> findByTag(String tag);

}
