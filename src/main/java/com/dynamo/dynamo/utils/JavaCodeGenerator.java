package com.dynamo.dynamo.utils;

import com.dynamo.dynamo.model.ProblemDetails;
import com.dynamo.dynamo.model.Type;

public class JavaCodeGenerator implements CodeGenerator {
    @Override
    public String generateCodeSkeleton(ProblemDetails problemDetails) {
        StringBuilder code = new StringBuilder();
        code.append("class Solution { ");
        code.append("<br>");
        code.append("public  ");
        code.append(typeToLangEquivalent(problemDetails.getReturnType()))
                .append(" ")
                .append(problemDetails.getMethodName())
                .append("(")
                .append(" ");

        problemDetails.getParameters().forEach(i -> {
            code.append(typeToLangEquivalent(i.getType())).
                    append(" ").
                    append(i.getName()).append(", ");
        });
        code.setLength(Math.max(code.length() - 2, 0));
        code.append(") {").append("<br> <b>");


        code.append(" // Your code here").append("<br>");

        code.append(" </b>}").append("<br>");
        code.append("}").append("<br>");
        return code.toString();
    }

    @Override
    public String typeToLangEquivalent(Type rawType) {

            switch (rawType) {
                case DOUBLE:
                    return "double";
                case FLOAT:
                    return "float";
                case CHAR:
                    return "char";
                case STRING:
                    return "String";
                case LONG:
                    return "long";
                default:
                    return "int";
            }
    }
}
