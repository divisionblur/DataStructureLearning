/**
 * @author joy division
 * @date 2020/10/27 12:16
 */
public class main {
    public static int numWays(int n) {
        if (n == 0) {
            return 1;
        }
        if (n <= 2) {
            return n;
        }
        return numWays(n - 1) + numWays(n - 2);
    }
    public static void main(String[] args) {
        System.out.println(numWays(5));
    }
}
