package zfar.org.java.shoot;

public final class Utils {
    public static void sleep(long millis) {
        sleep(millis, "sleeping interrupted!");
    }

    public static void sleep(long millis, String msg) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(msg);
        }
    }
}
