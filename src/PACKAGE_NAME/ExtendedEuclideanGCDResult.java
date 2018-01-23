package PACKAGE_NAME;

public class ExtendedEuclideanGCDResult {

    // Calculates gcd(a, b), and factors such that:
    // a*s + b*t = gcd(a, b)
    // a*s_ + b*t_ = 0
    public static ExtendedEuclideanGCDResult calculateGCD(long a, long b) {
        long[] v = new long[]{a, b, 1, 0, 0, 1};
        while (v[1] != 0) {
            long q = v[0] / v[1];
            v = new long[]{
                v[1],
                v[0] - q * v[1],
                v[3],
                v[2] - q * v[3],
                v[5],
                v[4] - q * v[5],};
        }
        return new ExtendedEuclideanGCDResult(a, b, v[0], v[2], v[4], v[3], v[5]);
    }

    private final long a;
    private final long b;
    public final long gcd;
    public final long s;
    public final long t;
    public final long s_;
    public final long t_;

    private ExtendedEuclideanGCDResult(long a, long b, long gcd, long s, long t, long s_, long t_) {
        this.a = a;
        this.b = b;
        this.gcd = gcd;
        this.s = s;
        this.t = t;
        this.s_ = s_;
        this.t_ = t_;
        assert (a * s + b * t == gcd);
        assert (a * s_ + b * t_ == 0);
    }

    public long getGCD() {
        return gcd;
    }

    public long getS() {
        return s;
    }

    public long getT() {
        return t;
    }

    public long getS_() {
        return s_;
    }

    public long getT_() {
        return t_;
    }

    @Override
    public String toString() {
        return a + "*" + s + "+" + b + "*" + t + " = " + gcd + ",\t"
                + a + "*" + s_ + "+" + b + "*" + t_ + " = " + 0;
    }

}
