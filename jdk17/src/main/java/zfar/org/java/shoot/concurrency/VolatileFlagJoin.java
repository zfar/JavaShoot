package zfar.org.java.shoot.concurrency;

import zfar.org.java.shoot.Utils;

/**
 * because of the join(), the sub thread can read the value of the variable flag
 * after the flag was set false
 */
public class VolatileFlagJoin {
    private static boolean flag = true;

    public static void main(String[] args) {
        Thread t1 = new Thread( () -> {
            while(flag) {
                System.out.println(flag);
                Utils.sleep(100, "sub thread sleeping interrupted");
            }
            System.out.println("子线程执行完毕");
        });
        t1.start();
        try {
            Thread.sleep(1000);
            flag = false;
            t1.join();
        } catch (InterruptedException e) {
            System.out.println("sleeping interrupted");
        }
    }
}
