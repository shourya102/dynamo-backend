package com.dynamo.dynamo.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityPostCommentResponse {
    private Long id;

    private String comment;

    private  Integer likes ;
    private Integer dislikes ;
    private  String username;


}
