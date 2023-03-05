package infrastructure.JDBCRepo;

import org.sqlite.SQLiteDataSource;
import java.sql.*;

import infrastructure.IRepository;

import java.io.IOException;
import java.sql.SQLException;

abstract public class jdbcRepo<ID, T> implements IRepository<ID, T> {
    protected String JDBC_URL;

    protected Connection conn = null;

    public jdbcRepo(String path) {
        JDBC_URL = "jdbc:sqlite:" + path;
    }

    protected void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);

            if (conn == null || conn.isClosed()) {
                conn = ds.getConnection();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    abstract protected void createSchema();

    abstract protected IRepository readData();
    abstract protected void deleteData(ID id);
    abstract protected void addData(ID id, T elem) throws SQLException;
    abstract protected void modifyData(ID id,T elem);

    @Override
    public void add(ID id, T elem) throws IOException, SQLException {
        addData(id, elem);
    }

    @Override
    public void delete(ID id) throws IOException, SQLException {
        deleteData(id);
    }

    @Override
    public void modify(ID id, T elem) throws IOException, SQLException {
        modifyData(id, elem);
    }

    @Override
    public T findById(ID id) throws IOException {
        IRepository<ID, T> repository = readData();

        return repository.findById(id);
    }

    @Override
    public Iterable<T> getAll() throws IOException {
        IRepository<ID, T> repository = readData();

        return repository.getAll();
    }

    @Override
    public String toString() {
        try {
            return getAll().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
