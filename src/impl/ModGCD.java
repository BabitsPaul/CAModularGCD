package impl;

import crt.Crt;

public class ModGCD
{
	public static Polynomial mod_gcd(Polynomial a, Polynomial b)
	{
		// swap polynomials if necessary
		if(a.getDegree() < b.getDegree() ||
				a.getDegree() == b.getDegree() && a.getLeadingCoefficient() < b.getLeadingCoefficient() )
		{
			Polynomial tmp = a;
			a = b;
			b = tmp;
		}

		PrimeGenerator primeGen = new PrimeGenerator();
		primeGen.getAsInt();
		primeGen.getAsInt();

		Polynomial g;

		long d = ExtendedEuclideanGCDResult.calculateGCD(a.getLeadingCoefficient(), b.getLeadingCoefficient()).gcd;
		long M = 2 * d * landauMignotteBound(a, b);
		M = 221;

		long p;

		do {
			p = nextElegiblePrime(primeGen, d);

			Polynomial cp = gcd_modulo(a, b, p);
			Polynomial gp = cp.mul(d % p);

			if (gp.monomialCount() == 0)
				return Polynomial.zeroPolynomial(1);

			long P = p;
			g = gp;

			while (P <= M) {
				p = nextElegiblePrime(primeGen, d);

				cp = gcd_modulo(a, b, p);
				gp = cp.mul(d % p);

				if (gp.monomialCount() < g.monomialCount()) {
					if (gp.monomialCount() == 0)
						return Polynomial.zeroPolynomial(1);

					P = p;
					g = gp;
				} else if (gp.monomialCount() == g.monomialCount()) {
					g = Crt.cra(g, gp, P, p);
					P *= p;
				}
			}

			g = g.primitivePolynomial();
		}while(a.div_mod_modular(g, p).getValue().monomialCount() != 0 ||
				b.div_mod_modular(g, p).getValue().monomialCount() != 0);

		return g;
	}

	private static long landauMignotteBound(Polynomial a, Polynomial b)
	{
		long tmp = (1 << b.monomialCount()) * Math.abs(b.getLeadingCoefficient() / a.getLeadingCoefficient());

		tmp *= (long) Math.sqrt(a.getCoefficients().stream().mapToLong(c -> c * c).sum());

		return tmp;
	}

	private static long nextElegiblePrime(PrimeGenerator gen, long d)
	{
		long p;

		while(d % (p = gen.getAsInt()) == 0);

		return p;
	}

	public static Polynomial gcd_modulo(Polynomial a, Polynomial b, long p)
	{
		a = a.mod(p);
		b = b.mod(p);

		// swap polynomials if necessary
		if(a.getDegree() < b.getDegree())
		{
			Polynomial tmp = a;
			a = b;
			b = tmp;
		}

		Polynomial tmp;

		while(b.getDegree() > 0)
		{
			tmp = b;
			b = a.div_mod_modular(b, p).getValue();
			a = tmp;
		}

		return a.toPositiveCoefficients(p).primitivePolynomial().normalizeByLP(p);
	}

	public static long multiplicativeInverse(long n, long p)
	{
		// n * x + p * y = 1
		// n * x - 1 = (-y) * m
		// n * x = 1 (mod m)

		// correct n if negative
		if(n < 0)
			n = correctNegativeMod(n, p);

		ExtendedEuclideanGCDResult r = ExtendedEuclideanGCDResult.calculateGCD(n, p);

		if(r.getGCD() != 1)
			throw new IllegalArgumentException("Values are not coprime - failed to calculate inverse for " + n + " " + p);

		return r.getS();
	}

	public static long modularDivision(long denom, long num, long p)
	{
		// returns n s.t. num * n = denom (mod p)
		return correctNegativeMod((denom % p) * multiplicativeInverse(num, p) , p);
	}

	private static long correctNegativeMod(long v, long p)
	{
		return (v % p + p) % p;
	}
}
