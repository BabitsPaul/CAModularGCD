package arithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimeGenerator
	implements Iterator<Long>
{
	private List<Long> primes;

	private int pos;

	public PrimeGenerator()
	{
		this(2L);
	}

	public PrimeGenerator(long min)
	{
		if(min < 2)
			throw new IllegalArgumentException("Minimum must be at least 2");

		// setup position to start returning primes from
		switch ((int) min)
		{
			case 2: pos = 0;	break;
			case 3: pos = 1;	break;
			default: pos = 2;	break;
		}

		// initialize prime-list with minimum-numbers
		primes = new ArrayList<>();
		primes.add(2L);
		primes.add(3L);

		// generate all primes s.t. the last generated prime is larger than min
		long p = nextPrime();
		while(p < min)
		{
			primes.add(p);
			pos++;

			p = nextPrime();
		}
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Long next()
	{
		primes.add(nextPrime());

		return primes.get(pos++);
	}

	private long nextPrime()
	{
		long np = primes.get(primes.size() - 1);
		boolean isPrime;

		do {
			np += 2;

			isPrime = true;

			for(long p : primes)
			{
				if (np % p == 0) {
					isPrime = false;
					break;
				}

				if(p * p >= np)
					break;
			}
		}while(!isPrime);

		return np;
	}
}
