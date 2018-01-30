package crt;

import impl.Polynomial;

import java.util.*;

public class Crt {
	public static int cra(LongObj x, long [] a, long [] m) { //m : array of moduli
		long M, xBis;

		LongObj u, v, pgcd;
		u = new LongObj();
		v = new LongObj();
		pgcd = new LongObj();

		M = m[0];
		xBis = a[0];
		for(int i = 1 ; i < a.length ; i++)
		{
			Euclide.extended_euclidean_algorithm(u, v, pgcd, m[i], M);
			xBis = u.value * m[i] * xBis + v.value * M * a[i];
			M = m[i] * M;
			xBis = xBis % M;
		}

		if(xBis == a[0]) {
			return 0;
		}
		else {
			x.value = xBis;
			return 1;
		}
	}

	public static Polynomial cra(Polynomial x, Polynomial y, long P, long p)
	{
		LongObj lob = new LongObj();
		long[] a = new long[2];
		long[] m = new long[]{P, p};

		Map<Polynomial.Exponents, Long> monomialsX = x.monomials();
		Map<Polynomial.Exponents, Long> monomialsY = y.monomials();
		Map<Polynomial.Exponents, Long> result = new HashMap<>();

		Set<Polynomial.Exponents> exps = new HashSet<>(monomialsX.keySet());
		exps.addAll(monomialsY.keySet());

		for(Polynomial.Exponents e : exps)
		{
			a[0] = Optional.ofNullable(monomialsX.get(e)).orElse(0l);
			a[1] = Optional.ofNullable(monomialsY.get(e)).orElse(0l);

			cra(lob, a, m);

			result.put(e, lob.value);
		}

		Polynomial poly = new Polynomial(x.nVars());
		poly.monomials().putAll(result);

		return poly;
	}
}
