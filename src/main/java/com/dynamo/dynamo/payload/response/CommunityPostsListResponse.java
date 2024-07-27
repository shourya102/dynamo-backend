package com.dynamo.dynamo.payload.response;

import com.dynamo.dynamo.model.CommunityPostTag;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class CommunityPostsListResponse {

    private Long id;
    private String title ;
    private Integer likes;
    private Integer dislikes;
    private Set<String> tags ;

}
