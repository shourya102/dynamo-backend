package com.dynamo.dynamo.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CommunityPostRequest {

    @NotEmpty
    private String title ;
    @NotEmpty
    private String description;
    private List<String> tags ;

}
