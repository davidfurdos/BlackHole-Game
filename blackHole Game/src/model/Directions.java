package model;

public enum Directions {
    UP,DOWN,LEFT,RIGHT;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}