package com.dynamo.dynamo.payload.response;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ErrorMessage {

    private Integer statusCode;
    private Date date;
    private String Message;
    private String description;
}
