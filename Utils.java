public class Utils {

    public void sleep(int x) {
        try {
            Thread.sleep(x * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
