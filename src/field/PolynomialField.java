package field;

import polynomial.Polynomial;

public class PolynomialField
	implements Field<Polynomial>
{
	private FiniteIntField field;

	public PolynomialField(long p)
	{
		field = new FiniteIntField(p);
	}

	@Override
	public Polynomial add(Polynomial a, Polynomial b) {
		return null;
	}

	@Override
	public Polynomial sub(Polynomial a, Polynomial b) {
		return null;
	}

	@Override
	public Polynomial mul(Polynomial a, Polynomial b) {
		return null;
	}

	@Override
	public Polynomial div(Polynomial a, Polynomial b) {
		return null;
	}

	@Override
	public Polynomial mod(Polynomial a, Polynomial b) {
		return null;
	}
}
