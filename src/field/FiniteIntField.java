package field;

import arithm.Arithm;

public class FiniteIntField
	implements Field<Long>
{
	private long p;

	public FiniteIntField(long p)
	{
		this.p = p;
	}

	@Override
	public Long add(Long a, Long b) {
		return Arithm.inField(a + b, p);
	}

	@Override
	public Long sub(Long a, Long b) {
		return Arithm.inField(a - b, p);
	}

	@Override
	public Long mul(Long a, Long b) {
		return Arithm.inField(a * b, p);
	}

	@Override
	public Long div(Long a, Long b) {
		return (a * Arithm.inField(b, p)) % p;
	}

	@Override
	public Long mod(Long a, Long b) {
		return Arithm.inField(a, p) % Arithm.inField(b, p);
	}
}
