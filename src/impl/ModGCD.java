package impl;

import crt.Crt;

public class ModGCD
{
	public static Polynomial mod_gcd(Polynomial a, Polynomial b)
	{
		// swap polynomials if necessary
		if(a.getDegree() < b.getDegree())
		{
			Polynomial tmp = a;
			a = b;
			b = tmp;
		}

		PrimeGenerator primeGen = new PrimeGenerator();

		Polynomial g;

		long d = ExtendedEuclideanGCDResult.calculateGCD(a.getLeadingCoefficient(), b.getLeadingCoefficient()).gcd;
		long M = 2 * d * landauMignotteBound(a, b);

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
		}while(a.div_mod(g).getValue().monomialCount() != 0 || b.div_mod(g).getValue().monomialCount() != 0);

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

	private static Polynomial gcd_modulo(Polynomial a, Polynomial b, long p)
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

			// b = b.mod(p);
		}

		return a;
	}
}
