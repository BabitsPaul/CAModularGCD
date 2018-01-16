package PACKAGE_NAME;

public class Monomial implements Comparable<Monomial> {

    private final long coefficient;
    private final long[] exponents;

    public Monomial(long coefficient, long[] exponents) {
        this.coefficient = coefficient;
        this.exponents = exponents;
    }

    public long getCoefficient() {
        return coefficient;
    }

    public long[] getExponents() {
        return exponents.clone();
    }

    @Override
    public int compareTo(Monomial o) {
        if (exponents.length != o.exponents.length) {
                throw new IllegalArgumentException("Number of variables does not match");
        }
        for (int i = 0; i < exponents.length; i++) {
            int diff = Long.compare(exponents[i], o.exponents[i]);
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (coefficient > 0) {
            sb.append('+');
        }
        sb.append(coefficient);
        for (int i = 0; i < exponents.length; i++) {
            sb.append(" * x_").append(i).append('^').append(exponents[i]);
        }
        return sb.toString();
    }

}
