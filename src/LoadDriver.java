import db.BenchmarkDB;
import utils.ParameterGenerator;

public class LoadDriver extends Thread {

    private int count = 0;

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        try (BenchmarkDB db = new BenchmarkDB("192.168.122.36",  "postgres", "dbidbi")) {
            long duration = System.currentTimeMillis();
            long now;
            int f;
            while (true) {
                f = ParameterGenerator.getInt(0, 100);
                if (f <= 35) {
                    db.getBalance(ParameterGenerator.getInt(1, 10000000));
                } else if (f <= 85) {
                    db.deposit(ParameterGenerator.getInt(1, 10000000), ParameterGenerator.getInt(1, 1000), ParameterGenerator.getInt(1, 100), ParameterGenerator.getInt(1, 10000));
                } else {
                    db.analyse(ParameterGenerator.getInt(1, 10000));
                }

                now = System.currentTimeMillis() - duration;

                if (now > 240000 && now < 540000) {
                    count++;
                }
                if (now > 600000) {
                    return;
                }

                Thread.sleep(50);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
