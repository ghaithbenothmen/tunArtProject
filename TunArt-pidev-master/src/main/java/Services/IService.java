package Services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void add(T t) throws SQLException;

    boolean delete(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    T findById(T t) throws SQLException;

    List<T> findAll() throws SQLException;

}
