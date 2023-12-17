package ru.vsu.cs.shi;

public enum Color {
    WHITE,
    BlACK;

    public Color opposite() {
        return this == WHITE ? BlACK : WHITE;
    }
}
