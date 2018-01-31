package polynomial;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Polynomial
{
	private final int nVars;

	private final Map<Exponents, Long> monomials;

	public Polynomial(int nVars)
	{
		this(nVars, new HashMap<>());
	}

	public Polynomial(int nVars, Map<Exponents, Long> monomials)
	{
		this.nVars = nVars;
		this.monomials = monomials;

		monomials.keySet().forEach(e -> {
			if(monomials.get(e) == 0)
				monomials.remove(e);
		});
	}

	public Set<Exponents> listExponents()
	{
		return monomials.keySet();
	}

	public Long getCoefficient(Exponents e)
	{
		return monomials.getOrDefault(e, 0l);
	}
}
