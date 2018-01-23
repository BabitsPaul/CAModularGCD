package impl;

import java.util.Comparator;
import java.util.Optional;

public class Polynomial_
{
	private long[][] monomials;

	public Polynomial_()
	{

	}

	private class Monomial
	{
		private long coefficient;

		private long[] exponents;

		public Monomial(int nVars)
		{
			this(0, new long[nVars]);
		}

		public Monomial(long coefficient, long[] exponents)
		{
			this.coefficient = coefficient;
			this.exponents = exponents;
		}

		public Comparator<Monomial> compareByVar(int i)
		{
			return Comparator.comparing(m -> m.exponents[i]);
		}

		public Comparator<Monomial> lexComparator()
		{
			Comparator<Monomial> comp = compareByVar(0);

			for(int i = 1; i < exponents.length; i++)
			{
				comp = comp.thenComparing(compareByVar(i));
			}

			return comp;
		}

		public long getCoefficient()
		{
			return coefficient;
		}

		public long getExponent(int var)
		{
			return exponents[var];
		}
	}

	private class ExpansibleMonomialList
	{
		private Monomial[] monomials;

		private int nVars;

		public ExpansibleMonomialList(int initCapacity, int nVars)
		{
			monomials = new Monomial[initCapacity];

			this.nVars = nVars;
		}

		public void put(int i)
		{

		}

		public Monomial get(int i)
		{
			return Optional.of(monomials[i]).orElse(new Monomial(nVars));
		}
	}
}
