package test;

import arithm.CRA;
import arithm.PrimeGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

import java.util.*;

@RunWith(Parameterized.class)
public class TestCRAInt
{
	private static int MAX_PRIMES = 100000;

	private static int TEST_CASES = 10000;

	@Parameter
	public long n;

	@Parameter(1)
	public long p1;

	@Parameter(2)
	public long p2;

	@Parameters
	public static Collection<Object[]> parameters()
	{
		// generate primes
		TreeSet<Long> primes = new TreeSet<>();
		PrimeGenerator pg = new PrimeGenerator();
		for(int i = 0; i < MAX_PRIMES; i++)
			primes.add(pg.next());

		// generate test-cases
		Collection<Object[]> tc = new ArrayList<>();
		long upperBound = primes.last();
		Random rnd = new Random();

		// n = u * l, l first chosen after n
		// l = 2, n = upperbound
		// u > n / 2

		for(int i = 0; i < TEST_CASES; i++)
		{
			long n = Math.abs(rnd.nextLong()) % upperBound + 1;
			long l = Math.abs(rnd.nextLong()) % (long) Math.sqrt(n);
			long u = primes.ceiling(n / l + n % l);

			tc.add(new Object[]{n, l, u});
		}

		return tc;
	}

	@Test
	public void testCRAInt()
	{
		long crt = CRA.cra(n % p1, n % p2, p1, p2);

		String msg = String.format("n = %10d  p1 = %d10    p2 = %d10   crt = %d10 ", n, p1, p2, crt);

		assertEquals(msg, n, crt);
	}
}
