package field;

public class IntField
	implements Field<Long>
{
	@Override
	public Long add(Long a, Long b) {
		return a + b;
	}

	@Override
	public Long sub(Long a, Long b) {
		return a - b;
	}

	@Override
	public Long mul(Long a, Long b) {
		return a * b;
	}

	@Override
	public Long div(Long a, Long b) {
		return a / b;
	}

	@Override
	public Long mod(Long a, Long b) {
		return a % b;
	}
}
