package Entites;

public enum TypeOeuvre {
    CHANSON,
    PEINTURE,
    FILM,
    MUSIQUE,
    PIECEDANCE;

    public static TypeOeuvre getChanson(){return CHANSON;}
    public static TypeOeuvre getPeinture(){return PEINTURE;}
    public static TypeOeuvre getFilm(){return FILM;}
    public static TypeOeuvre getMusique(){return MUSIQUE;}
    public static TypeOeuvre getPiecedance(){return PIECEDANCE;}
}
