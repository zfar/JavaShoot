package zfar.org.java.shoot.concurrency;

import zfar.org.java.shoot.Utils;

public class VolatileFlagNothing {
//    private static volatile boolean flag = true;
    private static boolean flag = true;
    public static void main(String[] args) {
        Thread runner = new Thread(() -> {
            int n = 0;
            while(flag) {
                // will make the flag without volatile visible
                // System.out.println(flag);

                // will make the flag without volatile visible
                // Utils.sleep(0); // just release the cpu

                ++n;
            }
            System.out.println("runner thread exited: " + n);
        });
        runner.start();
        Utils.sleep(1);

        Thread controller = new Thread(() -> {
            flag = false;
            System.out.println("flag has been set:" + flag);
        });
        controller.start();

    }
}
