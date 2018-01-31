package test;

import arithm.PrimeGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TestPrimeGen
{
	@Test
	public void test()
	{
		PrimeGenerator pg = new PrimeGenerator();

		Scanner s;

		try
		{
			s = new Scanner(new File("primelist.txt"));
		}catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		while(s.hasNextInt())
		{
			long exp = s.nextLong();
			long p = pg.next();

			System.out.println(exp + " " + p);

			assertEquals(exp, p);
		}
	}
}
