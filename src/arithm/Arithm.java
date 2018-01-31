package arithm;

public class Arithm
{
	public static long gcd(long a, long b)
	{
		return GCD.calculateGCD(a, b).getGCD();
	}

	public static long inverseModular(long n, long p)
	{
		// n * x + p * y = 1
		// n * x - 1 = (-y) * m
		// n * x = 1 (mod m)

		// correct n if negative
		if(n < 0)
			n = inField(n, p);

		GCD r = GCD.calculateGCD(n, p);

		if(r.getGCD() != 1)
			throw new IllegalArgumentException("Values are not coprime - failed to calculate inverse for " + n + " " + p);

		return r.getS();
	}

	public static long inField(long v, long p)
	{
		return (v % p + p) % p;
	}
}
