package Services;

import Entites.Commentaire;

import java.sql.SQLException;
import java.util.List;

public interface IServiceCommentaire<T> {

    public void ajouter(T t) throws SQLException;
    public void modifier(T t)throws SQLException;;
    public void supprimer(T t)throws SQLException;;
    List<T> afficher()throws SQLException;
    T findById(int id)throws SQLException;

}
