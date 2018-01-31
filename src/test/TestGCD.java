package test;

import arithm.Arithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class TestGCD
{
	private static final int TEST_CASE_CT = 10000;

	// fields used together with @Parameter must be public
	@Parameter(0)
	public int a;
	@Parameter(1)
	public int b;


	// creates the test data
	@Parameters
	public static Collection<Object[]> data() {
		Collection<Object[]> result = new ArrayList<>();
		Random rnd = new Random();

		for(int i = 0; i < TEST_CASE_CT; i++)
			result.add(new Object[]{Math.abs(rnd.nextInt()), Math.abs(rnd.nextInt())});

		return result;
	}


	@Test
	public void testGCD()
	{
		long gcd = Arithm.gcd(a, b);

		assertTrue(a % gcd == 0);
		assertTrue(b % gcd == 0);
		assertTrue(Arithm.gcd(a / gcd, b / gcd) == 1);
	}
}