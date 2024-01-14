package com.dynamo.dynamo.utils;

import com.dynamo.dynamo.model.ProblemDetails;
import com.dynamo.dynamo.model.Type;

public class CCodeGenerator implements CodeGenerator {
    @Override
    public String generateCodeSkeleton(ProblemDetails problemDetails) {
        StringBuilder code = new StringBuilder();
        code.append(typeToLangEquivalent(problemDetails.getReturnType())).
                append(problemDetails.getMethodName()).
                append("(");
        problemDetails.getParameters().forEach(i -> {
            code.append(typeToLangEquivalent(i.getType())).
                    append(" ").
                    append(i.getName()).append(", ");
        });
        code.setLength(Math.max(code.length() - 2, 0));
        code.append(") {<br>").
                append("<b><b>}");
        return code.toString();
    }

    public String typeToLangEquivalent(Type rawType) {
        String type;
        switch (rawType) {
            case DOUBLE -> type = "double";
            case FLOAT -> type = "float";
            case CHAR -> type = "char";
            case STRING -> type = "char*";
            case LONG -> type = "long int";
            default -> type = "int";
        }
        return type;
    }
}
