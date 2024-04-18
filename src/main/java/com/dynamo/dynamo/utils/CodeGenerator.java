package com.dynamo.dynamo.utils;

import com.dynamo.dynamo.model.problem.EType;
import com.dynamo.dynamo.model.problem.ProblemDetails;

public interface CodeGenerator {
    String generateCodeSkeleton(ProblemDetails problemDetails);

    String typeToLangEquivalent(EType rawType);
}
