import db.BenchmarkDB;
import utils.ParameterGenerator;

public class TestSuite extends Thread {

    private int count = 0;
    private int threadId;

    public int getCount() {
        return count;
    }

    TestSuite(int threadId) {
        super();
        this.threadId = threadId;
    }

    @Override
    public void run() {
        try (BenchmarkDB db = new BenchmarkDB("192.168.122.36",  "postgres", "dbidbi")) {
            long start = System.currentTimeMillis();
            long duration;

            int p;
            while (true) {
                p = ParameterGenerator.getInt(0, 100);
                if (p <= 35) {
                    db.getBalance(ParameterGenerator.getInt(1, 10000000));
                } else if (p <= 85) {
                    db.deposit(ParameterGenerator.getInt(1, 10000000), ParameterGenerator.getInt(1, 1000), ParameterGenerator.getInt(1, 100), ParameterGenerator.getInt(1, 10000));
                } else {
                    db.analyse(ParameterGenerator.getInt(1, 10000));
                }

                duration = System.currentTimeMillis() - start;

                // start to count between 4 and 9 minutes
                if (duration > 240000 && duration < 540000) {
                    count++;
                }

                //end test after 10 minutes
                if (duration > 600000) {
                    return;
                }

                Thread.sleep(50);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
