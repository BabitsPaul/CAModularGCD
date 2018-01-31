package arithm;

import field.FiniteIntField;
import impl.Polynomial;

public class CRA
{
	public static long cra(long a, long b, long p, long q)
	{
		FiniteIntField f = new FiniteIntField(p * q);

		long p1 = Arithm.inverseModular(p, q);
		long q1 = Arithm.inverseModular(q, p);

		return f.add(f.mul(a, f.mul(q, q1)), f.mul(b, f.mul(p, p1)));
	}

	public static Polynomial cra(Polynomial a1, Polynomial a2, long p1, long p2)
	{
		return null;
	}

	public static void main(String[] args)
	{PrimeGenerator pg = new PrimeGenerator();
		for(int i = 0; i < 100000 - 1; i++)
			pg.next();

		long p = pg.next();
		System.out.println(Integer.MAX_VALUE / p);
	}
}
