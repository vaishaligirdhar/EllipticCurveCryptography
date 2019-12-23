import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws Exception {
        EllipticCurveUtils utils = new EllipticCurveUtils();
        int N = 1000;


        // Problem 4
        //checking driver function with given values
        {
            String problemId = "4";
            BigInteger p = new BigInteger(String.valueOf((int)(Math.pow(2, 16) - 17))); // 2^17 - 17
            BigInteger d = new BigInteger("154");
            BigInteger n = new BigInteger("16339");
            BigInteger[] a = {new BigInteger("12"), new BigInteger("61833")};
            System.out.println("Executing problem " + problemId + "...");
            utils.driver(a, d, p, n, N);
        }

        // Problem 5a
        {
            String problemId = "5a";
            BigInteger p = new BigInteger(String.valueOf((int)(Math.pow(2, 18) - 5))); // 2^17 - 17
            BigInteger d = new BigInteger("294");
            BigInteger n = new BigInteger("65717");
            BigInteger[] a = {new BigInteger("5"), new BigInteger("261901")};
            System.out.println("Executing problem " + problemId + "...");

            utils.driver(a, d, p, n, N);
        }

        // Problem 5b
        {
            String problemId = "5b";
            BigInteger p = new BigInteger(String.valueOf((int)(Math.pow(2, 20) - 5))); // 2^17 - 17
            BigInteger d = new BigInteger("47");
            BigInteger n = new BigInteger("262643");
            BigInteger[] a = {new BigInteger("3"), new BigInteger("111745")};
            System.out.println("Executing problem " + problemId + "...");
            utils.driver(a, d, p, n, N);
        }

        // Problem 5c
        {
            String problemId = "5c";
            BigInteger p = new BigInteger(String.valueOf((int)(Math.pow(2, 22) - 17))); // 2^17 - 17
            BigInteger d = new BigInteger("314");
            BigInteger n = new BigInteger("1049497");
            BigInteger[] a = {new BigInteger("4"), new BigInteger("85081")};
            System.out.println("Executing problem " + problemId + "...");
            utils.driver(a, d, p, n, N);
        }
    }
}
