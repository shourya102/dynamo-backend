package com.dynamo.dynamo.model;

public enum Type {
    INTEGER,
    FLOAT,
    DOUBLE,
    CHAR,
    STRING,
    LONG;

    public static boolean contains(String name) {
        for (Type type : values())
            if (type.name().equalsIgnoreCase(name)) return true;
        return false;
    }
}
