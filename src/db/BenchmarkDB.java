package db;

import org.postgresql.PGConnection;
import org.postgresql.copy.CopyIn;
import org.postgresql.copy.CopyManager;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to establish a connection to the database and interact with it.
 */
public class BenchmarkDB implements AutoCloseable {

    private Connection conn;

    /**
     * Initializes a connection to the database
     *
     * @param ip       The database ip (localhost / 192.168.122.36)
     * @param user     Database username
     * @param password Database password
     * @throws SQLException Database error occurred
     */
    public BenchmarkDB(String ip, String user, String password) throws SQLException {
        conn = DriverManager.getConnection("jdbc:postgresql://" + ip + "/ntps", user, password);
    }

    public double getBalance(int accId) {
        return 0;
    }

    public void deposit(int accId, int tellerId, int branchId, double delta) {

    }

    public int analyse(double delta) {
        return 0;
    }

    /**
     * Removes all data from the database
     *
     * @throws SQLException Database error occurred
     */
    public void clearHistory() throws SQLException {
        conn.createStatement().execute("TRUNCATE history CASCADE;");
    }

    /**
     * Close the connection when the try-with-resources statement ends
     *
     * @throws SQLException Database error occurred
     */
    @Override
    public void close() throws SQLException {
        conn.close();
    }
}
