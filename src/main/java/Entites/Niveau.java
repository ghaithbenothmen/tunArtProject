package Entites;

public enum Niveau {
    FACILE,
    MOYEN,
    DIFFICILE;

    public static Niveau getFacile() {
        return FACILE;
    }

    public static Niveau getMoyen() {
        return MOYEN;
    }

    public static Niveau getDifficile() {
        return DIFFICILE;
    }
}
