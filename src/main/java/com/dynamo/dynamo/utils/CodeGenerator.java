package com.dynamo.dynamo.utils;

import com.dynamo.dynamo.model.ProblemDetails;
import com.dynamo.dynamo.model.Type;

public interface CodeGenerator {
    String generateCodeSkeleton(ProblemDetails problemDetails);

    String typeToLangEquivalent(Type rawType);
}
