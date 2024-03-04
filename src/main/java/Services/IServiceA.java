package Services;

import java.sql.SQLException;
import java.util.List;

public interface IServiceA <T>{

    public void add(T t) throws SQLException;
    public void deletea(T t) throws SQLException;


    public void updatea (T t) throws SQLException;

     T findById(int id) throws SQLException;

    List<T> findAll() throws SQLException;

}
