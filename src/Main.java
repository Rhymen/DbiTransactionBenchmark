import db.BenchmarkDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        int threadCount = Integer.parseInt(args[0]);

        System.out.println("[] creating db connection");
        try (BenchmarkDB db = new BenchmarkDB("192.168.122.36",  "postgres", "dbidbi")) {
            System.out.println("[] clearing history");
            db.clearHistory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("[] starting " + threadCount + " threads");

        TestSuite[] threads = new TestSuite[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new TestSuite(i);
            threads[i].start();
            System.out.println("[] started " + (i + 1) + ". thread");
        }

        try {
            int count = 0;
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
                System.out.println("[] thread " + (i + 1) + " count: " + threads[i].getCount());
                count += threads[i].getCount();
            }
            System.out.println("[] threads: " + threadCount + " overall: " + count + " overall/s: " + count / 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
