package polynomial;

import java.util.Arrays;

public class Exponents
{
	private final long[] exps;

	public Exponents(long[] exps)
	{
		this.exps = exps;
	}

	public long getExp(int idx)
	{
		return exps[idx];
	}

	public boolean equals(Object o)
	{
		return o != null && o instanceof Exponents
				&& Arrays.equals(((Exponents) o).exps, exps);
	}

	public int hashCode()
	{
		return Arrays.hashCode(exps);
	}
}