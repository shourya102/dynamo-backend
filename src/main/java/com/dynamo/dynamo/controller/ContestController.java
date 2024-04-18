package com.dynamo.dynamo.controller;

import com.dynamo.dynamo.services.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContestController {
    @Autowired
    ContestService contestService;
}
