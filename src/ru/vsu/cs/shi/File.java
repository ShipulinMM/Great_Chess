package ru.vsu.cs.shi;

public enum File {
    A, B, C, D, E, F, G, H, I, J;
    public static File fromChar(char c) {

        try {
            return File.valueOf(String.valueOf(c).toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
