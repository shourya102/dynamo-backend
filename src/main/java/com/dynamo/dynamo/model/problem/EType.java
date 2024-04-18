package com.dynamo.dynamo.model.problem;

public enum EType {
    INTEGER,
    FLOAT,
    DOUBLE,
    CHAR,
    STRING,
    LONG;

    public static boolean contains(String name) {
        for (EType type : values())
            if (type.name().equalsIgnoreCase(name)) return true;
        return false;
    }
}
