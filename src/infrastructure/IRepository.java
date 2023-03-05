package infrastructure;

import java.io.IOException;
import java.sql.SQLException;

public interface IRepository<ID, T> {
    public void add(ID id, T elem) throws IOException, SQLException;
    public void delete(ID id) throws IOException, SQLException;
    public void modify(ID id, T elem) throws IOException, SQLException;
    public T findById(ID id) throws IOException;

    public Iterable<T> getAll() throws IOException;
}
