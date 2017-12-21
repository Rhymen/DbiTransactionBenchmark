package db;

import java.sql.*;

/**
 * Class to establish a connection to the database and interact with it.
 */
public class BenchmarkDB implements AutoCloseable {

    private Connection conn;

    private static final String GET_BALANCE_SQL = "SELECT balance FROM accounts WHERE branchId = ?";
    private PreparedStatement getBalanceStmt;

    private static final String DEPOSIT_SQL = "";
    private PreparedStatement depositStmt;

    private static final String ANALYSE_SQL = "";
    private PreparedStatement analyseStmt;


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

        getBalanceStmt = conn.prepareStatement(GET_BALANCE_SQL);
        depositStmt = conn.prepareStatement(DEPOSIT_SQL);
        analyseStmt = conn.prepareStatement(ANALYSE_SQL);
    }

    public double getBalance(int accId) throws SQLException {
        getBalanceStmt.clearParameters();
        getBalanceStmt.setInt(1, accId);

        ResultSet rs = getBalanceStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public double deposit(int accId, int tellerId, int branchId, double delta) {
        PreparedStatement updateBranches = null;

        String updateBranchesString =
                "BEGIN" +
                    "UPDATE Branches" +
                    "SET Balance = Balance + ?" +
                    "WHERE branchId = ?" +
                    "UPDATE Tellers" +
                    "SET Balance = Balance + ?" +
                    "WHERE tellerId = ?" +
                    "UPDATE Accounts" +
                    "SET Balance = Balance + ?" +
                    "WHERE accId = ?" +
                "END";

        return 0;
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
