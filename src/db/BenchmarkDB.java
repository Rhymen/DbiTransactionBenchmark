package db;

import java.sql.*;

/**
 * Class to establish a connection to the database and interact with it.
 */
public class BenchmarkDB implements AutoCloseable {

    private Connection conn;

    private static final String GET_BALANCE_SQL = "SELECT fGetBalance(?);";
    private PreparedStatement getBalanceStmt;

    private static final String DEPOSIT_SQL = "SELECT fDeposit(?,?,?,?);";
    private PreparedStatement depositStmt;

    private static final String ANALYSE_SQL = "SELECT fAnalyse(?);";
    private PreparedStatement analyseStmt;


    /**
     * Initializes a connection to the database
     *
     * @param ip The database ip (localhost / 192.168.122.36)
     * @param user Database username
     * @param password Database password
     * @throws SQLException Database error occurred
     */
    public BenchmarkDB(String ip, String user, String password) throws SQLException {
        conn = DriverManager.getConnection("jdbc:postgresql://" + ip + "/ntps", user, password);

        getBalanceStmt = conn.prepareStatement(GET_BALANCE_SQL);
        depositStmt = conn.prepareStatement(DEPOSIT_SQL);
        analyseStmt = conn.prepareStatement(ANALYSE_SQL);
    }

    /**
     * Returns the balance of the account with the passed id.
     *
     * @param accId Account id
     * @return Balance of the account
     * @throws SQLException Database error occurred
     */
    public int getBalance(int accId) throws SQLException {
        getBalanceStmt.clearParameters();

        getBalanceStmt.setInt(1, accId);

        ResultSet rs = getBalanceStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Deposits money in an account and updates the teller and branch.
     *
     * @param accId Account id
     * @param tellerId Teller id
     * @param branchId Branch id
     * @param delta The delta of money
     * @return The new account balance
     * @throws SQLException Database error occurred
     */
    public int deposit(int accId, int tellerId, int branchId, int delta) throws SQLException {
        depositStmt.clearParameters();

        depositStmt.setInt(1, accId);
        depositStmt.setInt(2, tellerId);
        depositStmt.setInt(3, branchId);
        depositStmt.setInt(4, delta);

        ResultSet rs = depositStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Returns the amount of deposits with the passed delta.
     *
     * @param delta The delta of money
     * @return The amount of deposits with the passed delta
     * @throws SQLException Database error occurred
     */
    public int analyse(int delta) throws SQLException {
        analyseStmt.clearParameters();

        analyseStmt.setInt(1, delta);

        ResultSet rs = analyseStmt.executeQuery();
        rs.next();
        return rs.getInt(1);

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
