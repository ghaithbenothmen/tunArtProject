package Services;

import java.sql.SQLException;
import java.util.List;

public interface IOeuvre<O> {
    void add(O o) throws SQLException;

    boolean delete(O o) throws SQLException;

    boolean update(O o) throws SQLException;

    O findByRef(int reff) throws SQLException;

    List<O> findAll() throws SQLException;

}