package PACKAGE_NAME;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.util.Pair;

public class Polynomial {

    private final int nVars;
    private final HashMap<Exponents, Long> monomials;

    private final ArrayList<Long> coefficients;

    public Polynomial(ArrayList<Long> coefficients) {
        this.nVars = coefficients.size();
        this.coefficients = coefficients;
        this.monomials = new HashMap<>();
    }

    private Polynomial(int nVars) {
        this.coefficients = null;
        this.nVars = nVars;
        this.monomials = new HashMap<>();
    }

    private int n() {
        return nVars;
    }

    private Map<Exponents, Long> m() {
        return monomials;
    }

    public Polynomial add(Polynomial other) {
        if (nVars != other.n()) {
            throw new IllegalArgumentException("Number of variables does not match");
        }
        Polynomial result = new Polynomial(nVars);
        monomials.entrySet().stream().forEach((monomial) -> {
            Exponents exp = monomial.getKey();
            long coeff = monomial.getValue();
            result.m().put(exp, coeff);
        });
        other.m().entrySet().stream().forEach((monomial) -> {
            Exponents exp = monomial.getKey();
            long coeff = monomial.getValue();
            result.m().put(exp, (result.m().containsKey(exp) ? result.m().get(exp) : 0) + coeff);
        });
        return result.removeZeroCoefficients();
    }

    public Polynomial sub(Polynomial other) {
        if (nVars != other.n()) {
            throw new IllegalArgumentException("Number of variables does not match");
        }
        Polynomial result = new Polynomial(nVars);
        monomials.entrySet().stream().forEach((monomial) -> {
            Exponents exp = monomial.getKey();
            long coeff = monomial.getValue();
            result.m().put(exp, coeff);
        });
        other.m().entrySet().stream().forEach((monomial) -> {
            Exponents exp = monomial.getKey();
            long coeff = monomial.getValue();
            result.m().put(exp, (result.m().containsKey(exp) ? result.m().get(exp) : 0) - coeff);
        });
        return result.removeZeroCoefficients();
    }

    public Polynomial mul(Polynomial other) {
        if (nVars != other.n()) {
            throw new IllegalArgumentException("Number of variables does not match");
        }
        Polynomial result = new Polynomial(nVars);
        monomials.entrySet().stream().forEach((m1) -> {
            Exponents e1 = m1.getKey();
            long coeff1 = m1.getValue();
            other.m().entrySet().stream().forEach((m2) -> {
                Exponents e2 = m2.getKey();
                long coeff2 = m2.getValue();
                long[] exp = new long[nVars];
                for (int i = 0; i < exp.length; i++) {
                    exp[i] = e1.e(i) + e2.e(i);
                }
                Exponents e = new Exponents(exp);
                long coeff = (result.m().containsKey(e) ? result.m().get(e) : 0) + coeff1 * coeff2;
                result.m().put(e, coeff);
            });
        });
        return result.removeZeroCoefficients();
    }

    public Polynomial mul(long factor) {
        Polynomial result = new Polynomial(nVars);
        if (factor == 0) {
            return result;
        }
        monomials.entrySet().stream().forEach((m1) -> {
            Exponents e1 = m1.getKey();
            long coeff1 = m1.getValue();
            result.m().put(e1, coeff1 * factor);
        });
        return result;
    }

    public Polynomial div(Polynomial other) {
        return div_mod(other).getKey();
    }

    public Polynomial mod(Polynomial other) {
        return div_mod(other).getValue();
    }

    public Pair<Polynomial, Polynomial> div_mod(Polynomial other) {
        if (nVars != other.n()) {
            throw new IllegalArgumentException("Number of variables does not match");
        }
        Polynomial result = new Polynomial(nVars);
        {
            List<Entry<Exponents, Long>> myMonomials = getSortedMonomials();
            List<Entry<Exponents, Long>> otherMonomials = other.getSortedMonomials();
            Entry<Exponents, Long> myEntry = myMonomials.get(0);
            Exponents myLPP = myEntry.getKey();
            Entry<Exponents, Long> otherEntry = otherMonomials.get(0);
            Exponents otherLPP = otherEntry.getKey();
            if (myLPP.compareTo(otherLPP) > 0) {
                long[] exps = new long[nVars];
                for (int i = 0; i < exps.length; i++) {
                    exps[i] = myLPP.e(i) - otherLPP.e(i);
                }
                ExtendedEuclideanGCDResult e = ExtendedEuclideanGCDResult.calculateGCD(myEntry.getValue(), otherEntry.getValue());
                Polynomial newSelf = mul(e.s_);
                result.m().put(new Exponents(exps), e.t_);
                Polynomial remainder = newSelf.add(other.mul(result));
                return new Pair<>(result, remainder);
            } else {
                Polynomial remainder = new Polynomial(nVars);
                result.m().entrySet().stream().forEach((entry)
                        -> result.m().put(entry.getKey(), entry.getValue()));
                return new Pair<>(result, remainder);
            }
        }
    }

    public Polynomial mod(long p) {
        Polynomial result = new Polynomial(nVars);

        return result.removeZeroCoefficients();
    }

    private Polynomial removeZeroCoefficients() {
        Iterator<Entry<Exponents, Long>> iterator = monomials.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Exponents, Long> entry = iterator.next();
            if (entry.getValue() == 0) {
                iterator.remove();
            }
        }
        return this;
    }

    public static Polynomial parse(String poly_string, int nVars) {
        List<Pair<Long, String>> sr = new ArrayList<>();
        for (String s : poly_string.split("\\+")) {
            String[] i = s.split("-");
            if (i[0].length() > 0) {
                sr.add(new Pair<>(1l, i[0]));
            }
            for (int j = 1; j < i.length; j++) {
                sr.add(new Pair<>(-1l, i[j]));
            }
        }
        List<Pair<Long, Exponents>> res = new ArrayList<>();
        for (Pair<Long, String> p : sr) {
            String[] terms = p.getValue().split("\\*");
            long coeff = p.getKey() * Long.parseLong(terms[0]);
            long[] exps = new long[nVars];
            for (int i = 1; i < terms.length; i++) {
                String[] var = terms[i].split("\\^");
                exps[Integer.parseInt(var[0])] = Long.parseLong(var[1]);
            }
            res.add(new Pair<>(coeff, new Exponents(exps)));
        }
        Polynomial p = new Polynomial(nVars);
        res.stream().forEach((monomial) -> {
            Exponents exp = monomial.getValue();
            Long coeff = monomial.getKey();
            if (p.m().containsKey(exp)) {
                p.m().put(exp, p.m().get(exp) + coeff);
            } else {
                p.m().put(exp, coeff);
            }
        });
        return p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        getSortedMonomials().stream().forEach((monomial) -> {
            long coeff = monomial.getValue();
            Exponents e = monomial.getKey();
            if (coeff > 0) {
                sb.append(" + ");
            } else {
                sb.append(" - ");
            }
            sb.append(Math.abs(coeff));
            for (int i = 0; i < e.e.length; i++) {
                sb.append(" * x_").append(i).append("^").append(e.e(i));
            }
        });
        return sb.toString();
    }

    private List<Entry<Exponents, Long>> getSortedMonomials() {
        return monomials
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
                .collect(Collectors.toList());
    }

    private static class Exponents implements Comparable<Exponents> {

        private final long[] e;

        public Exponents(long[] e) {
            this.e = e;
        }

        public long e(int idx) {
            return e[idx];
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 59 * hash + Arrays.hashCode(this.e);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Exponents other = (Exponents) obj;
            return Arrays.equals(this.e, other.e);
        }

        @Override
        public int compareTo(Exponents o) {
            if (o.e.length != e.length) {
                throw new IllegalArgumentException("Number of variables does not match");
            }
            for (int i = 0; i < e.length; i++) {
                long diff = e(i) - o.e(i);
                if (diff != 0) {
                    if (diff > 0) {
                        return +1;
                    } else {
                        return -1;
                    }
                }
            }
            return 0;
        }

    }
}
