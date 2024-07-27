package com.dynamo.dynamo.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmitRequest {
    Long problemId;
    String code;
}
