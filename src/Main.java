import db.BenchmarkDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            BenchmarkDB db = new BenchmarkDB("192.168.122.36",  "postgres", "dbidbi");
            db.clearHistory();

            System.out.println("Balance: " + db.getBalance(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
