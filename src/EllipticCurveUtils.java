import java.math.BigInteger;
import java.util.Random;
import java.util.function.BiFunction;

public class EllipticCurveUtils {

    private BigInteger[] u = {BigInteger.ZERO, BigInteger.ONE};
    private BigInteger two = new BigInteger("2");
    private BigInteger three = new BigInteger("3");

    // Implementation of problem 1
    private BigInteger[] mul(BigInteger[] a1, BigInteger[] a2, BigInteger d, BigInteger p) {
        BigInteger x1 = a1[0], y1 = a1[1], x2 = a2[0], y2 = a2[1];

        BigInteger x1y2 = x1.multiply(y2).mod(p);
        BigInteger y1x2 = y1.multiply(x2).mod(p);
        BigInteger x1x2 = x1.multiply(x2).mod(p);
        BigInteger y1y2 = y1.multiply(y2).mod(p);
        BigInteger x1x2y1y2 = x1x2.multiply(y1y2).mod(p);
        BigInteger dx1x2y1y2 = d.multiply(x1x2y1y2).mod(p);

        BigInteger num1 = x1y2.add(y1x2).mod(p);
        BigInteger den1 = BigInteger.ONE.add(dx1x2y1y2).mod(p);

        BigInteger num2 = y1y2.subtract(x1x2).mod(p);
        BigInteger den2 = BigInteger.ONE.subtract(dx1x2y1y2).mod(p);

        BigInteger x3 = num1.multiply(den1.modInverse(p)).mod(p);
        BigInteger y3 = num2.multiply(den2.modInverse(p)).mod(p);

        return new BigInteger[]{x3, y3};
    }

    // Implementation of problem 2
    private BigInteger[] exp(BigInteger[] a, BigInteger m, BigInteger d, BigInteger p) {
        BigInteger[] b = new BigInteger[]{BigInteger.ZERO, BigInteger.ONE};

        for (int i = m.bitLength() - 1; i >= 0; i--) {
            b = mul(b, b, d, p);
            if (m.testBit(i)) {
                b = mul(b, a, d, p);
            }
        }
        return b;
    }

    // Implementation of problem 3
    private BigInteger[] rho(BigInteger[] a, BigInteger[] b, BigInteger d, BigInteger p, BigInteger n) throws Exception {
        BigInteger alphaK = BigInteger.ZERO;
        BigInteger betaK = BigInteger.ZERO;
        BigInteger[] zK = u;

        BigInteger alpha2K = BigInteger.ZERO;
        BigInteger beta2K = BigInteger.ZERO;
        BigInteger[] z2K = u;
        int k = 0; // iteration counter


        while (true) {
            switch (zK[0].mod(three).intValue()) {
                case 0:
                    zK = mul(b, zK, d, p);
                    alphaK = alphaK.add(BigInteger.ONE).mod(n);
                    break;
                case 1:
                    zK = mul(zK, zK, d, p);
                    alphaK = alphaK.multiply(two).mod(n);
                    betaK = betaK.multiply(two).mod(n);
                    break;
                case 2:
                    zK = mul(a, zK, d, p);
                    betaK = betaK.add(BigInteger.ONE).mod(n);
                    break;
            }

            for (int i = 0; i < 2; i++) {
                switch (z2K[0].mod(three).intValue()) {
                    case 0:
                        z2K = mul(b, z2K, d, p);
                        alpha2K = alpha2K.add(BigInteger.ONE).mod(n);
                        break;
                    case 1:
                        z2K = mul(z2K, z2K, d, p);
                        alpha2K = alpha2K.multiply(two).mod(n);
                        beta2K = beta2K.multiply(two).mod(n);
                        break;
                    case 2:
                        z2K = mul(a, z2K, d, p);
                        beta2K = beta2K.add(BigInteger.ONE).mod(n);
                        break;
                }
            }
            k++;
            if ((zK[0].equals(z2K[0]) && zK[1].equals(z2K[1]))) {
                break;
            } else if (k > 100000) {
                System.out.println("k exceeded the limit");
                break;
            }
        }


        BigInteger den = alphaK.subtract(alpha2K).mod(n);
        if (den.equals(BigInteger.ZERO)) {
            throw new Exception("Not able to calculate m; denominator is 0");
        }
        BigInteger num = beta2K.subtract(betaK).mod(n);
        BigInteger m = num.multiply(den.modInverse(n)).mod(n);
        return new BigInteger[]{m, new BigInteger(Integer.toString(k))};
    }

    // Implementation of problem 4 check method
    private Integer check(BigInteger[] a, BigInteger d, BigInteger p, BigInteger n) throws Exception {

        BigInteger m = new BigInteger(String.valueOf((int) (10000 * Math.random()))).mod(n);
        //calculating b such that b = a^m
        BigInteger[] b = exp(a, m, d, p);


        // reverse calculating m from generated b
        BigInteger[] mPrime = new BigInteger[0];
        mPrime = rho(a, b, d, p, n);

        if (!m.equals(mPrime[0])) {
            throw new Exception("m does not match with m'");
        } else {
            return mPrime[1].intValue();
        }
    }

    // Implementation of problem 4 driver method
    void driver(BigInteger[] a, BigInteger d, BigInteger p, BigInteger n, int N) throws Exception {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            Integer k = check(a, d, p, n);
            sum += k;
//            System.out.println("For " + i + "th iteration, k = " + k);
        }
        System.out.println("Average number of k for " + N + " calls = " + (sum / (N)));
    }
}
