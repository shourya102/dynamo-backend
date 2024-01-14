package com.dynamo.dynamo.model;

public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public static boolean contains(String name) {
        for (Difficulty difficulty : values())
            if (difficulty.name().equalsIgnoreCase(name)) return true;
        return false;
    }
}
