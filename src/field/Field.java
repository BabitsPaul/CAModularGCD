package field;

public interface Field<T>
{
	T add(T a, T b);

	T sub(T a, T b);

	T mul(T a, T b);

	T div(T a, T b);

	T mod(T a, T b);
}
