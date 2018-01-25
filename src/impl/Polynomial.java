package impl;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.util.Pair;

public class Polynomial {

    private final int nVars;
    private final Map<Exponents, Long> monomials;

    public Polynomial(int nVars) {
        this.nVars = nVars;
        this.monomials = new HashMap<>();
    }

    public Polynomial(Map<Exponents, Long> monomials, int nVars) {
        this.nVars = nVars;
        this.monomials = monomials;
    }

    private int n() {
        return nVars;
    }

    public Map<Exponents, Long> monomials() {
        return monomials;
    }

    public Polynomial copy() {
        Polynomial result = new Polynomial(nVars);
        result.monomials.putAll(monomials);
        return result;
    }

    public Polynomial add(Polynomial other) {
        if (nVars != other.n()) {
            throw new IllegalArgumentException("Number of variables does not match");
        }
        Polynomial result = new Polynomial(nVars);
        monomials
                .entrySet()
                .stream()
                .forEach((monomial)
                        -> result
                        .monomials()
                        .put(monomial.getKey(), monomial.getValue()));
        other
                .monomials()
                .entrySet()
                .stream()
                .forEach((monomial)
                        -> result
                        .monomials()
                        .put(monomial.getKey(), result.monomials().getOrDefault(monomial.getKey(), 0l) + monomial.getValue()));
        return result.removeZeroCoefficients();
    }

    public Polynomial sub(Polynomial other) {
        return add(other.mul(-1));
    }

    public Polynomial mul(Polynomial other) {
        if (nVars != other.n()) {
            throw new IllegalArgumentException("Number of variables does not match");
        }
        Polynomial result = new Polynomial(nVars);
        monomials
                .entrySet()
                .stream()
                .forEach((m1) -> {
                    Exponents e1 = m1.getKey();
                    long coeff1 = m1.getValue();
                    other.monomials().entrySet().stream().forEach((m2) -> {
                        Exponents e2 = m2.getKey();
                        long coeff2 = m2.getValue();
                        long[] exp = new long[nVars];
                        for (int i = 0; i < exp.length; i++) {
                            exp[i] = e1.e(i) + e2.e(i);
                        }
                        Exponents e = new Exponents(exp);
                        long coeff = result.monomials().getOrDefault(e, 0l) + coeff1 * coeff2;
                        result.monomials().put(e, coeff);
                    });
                });
        return result.removeZeroCoefficients();
    }

    public Polynomial mul(long factor) {
        Polynomial result = new Polynomial(nVars);
        if (factor == 0) {
            return result;
        }
        monomials
                .entrySet()
                .stream()
                .forEach((m1)
                        -> result
                        .monomials()
                        .put(m1.getKey(), m1.getValue() * factor));
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
        Polynomial remainder = copy();

        Exponents otherExp = other.getLeadingExponents();
        long[] otherPP = other.getLeadingPowerProduct();
        long otherCoeff = other.getLeadingCoefficient();

        while (remainder.getLeadingExponents().compareTo(otherExp) >= 0) {
            long[] remPP = remainder.getLeadingPowerProduct();
            long[] diff = new long[nVars];
            for (int i = 0; i < diff.length; i++) {
                diff[i] = remPP[i] - otherPP[i];
            }
            ExtendedEuclideanGCDResult e = ExtendedEuclideanGCDResult.calculateGCD(remainder.getLeadingCoefficient(), otherCoeff);
            
            Polynomial factor = new Polynomial(nVars);
            factor.monomials.put(new Exponents(diff), e.t_);
            
            result = result.sub(factor);
            remainder = remainder.mul(e.s_).add(other.mul(factor));
        }
        return new Pair<>(result, remainder);
        /*
		if (nVars != other.n() || nVars != 1) {
			throw new IllegalArgumentException("Number of variables does not match - must be 1");
		}

		// a = this, b = other

		Polynomial q = new Polynomial(1);
		Polynomial r = this;
		long d = other.getDegree();
		long c = other.getLeadingCoefficient();

		while(r.getDegree() >= d && r.getDegree() > 0)
		{
			long coeff = r.getLeadingCoefficient() / c;

			Map<Exponents, Long> sMonomials = new HashMap<>();
			sMonomials.put(new Exponents(new long[]{r.getDegree() - d}), coeff);

			// TODO division not integral!!!
			Polynomial s = new Polynomial(sMonomials, nVars);
			q = q.add(s);				// q = q + s
			r = r.sub(s.mul(other));	// r = r - s * b
		}

		return new Pair<>(q, r);
         */ 
    }

    public Pair<Polynomial, Polynomial> div_mod_modular(Polynomial other, long p) {
        // TODO modular division

        if (nVars != other.n() || nVars != 1) {
            throw new IllegalArgumentException("Number of variables does not match - must be 1");
        }

        // a = this, b = other
        Polynomial q = new Polynomial(1);
        Polynomial r = this;
        long d = other.getDegree();
        long c = other.getLeadingCoefficient();

        while (r.getDegree() >= d && r.getDegree() > 0) {
            // inverse of c
            long c_inv = ExtendedEuclideanGCDResult.calculateGCD(p, c).s;

            long coeff = r.getLeadingCoefficient() * c_inv % p;

            Map<Exponents, Long> sMonomials = new HashMap<>();
            sMonomials.put(new Exponents(new long[]{r.getDegree() - d}), coeff);

            Polynomial s = new Polynomial(sMonomials, nVars);
            q = q.add(s).mod(p);				// q = q + s
            r = r.sub(s.mul(other)).mod(p);	// r = r - s * b
        }

        return new Pair<>(q, r);
    }

    public Polynomial mod(long m) {
        Polynomial p = new Polynomial(nVars);

        monomials
                .entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue() % m))
                .filter(e -> e.getValue() != 0)
                .forEach(e -> p.monomials.put(e.getKey(), e.getValue()));

        return p;
    }

    public int monomialCount() {
        return monomials.size();
    }

    public int nVars() {
        return nVars;
    }

    public Collection<Long> getCoefficients() {
        return monomials.values();
    }

    public long getLeadingCoefficient() {
        return getSortedMonomials().get(0).getValue();
    }

    public Exponents getLeadingExponents() {
        return getSortedMonomials().get(0).getKey();
    }

    public long[] getLeadingPowerProduct() {
        return getLeadingExponents().e.clone();
    }

    private Polynomial removeZeroCoefficients() {
        Iterator<Entry<Exponents, Long>> iterator = monomials
                .entrySet()
                .iterator();
        while (iterator.hasNext()) {
            Entry<Exponents, Long> entry = iterator.next();
            if (entry.getValue() == 0) {
                iterator.remove();
            }
        }
        return this;
    }

    public static Polynomial parse(String poly_string, int nVars) {
        // normalize string (no spaces)
        poly_string = poly_string.replaceAll("\\s+", "");

        // get varcount
        int[] vars = poly_string.chars().filter(Character::isLowerCase).distinct().sorted().toArray();
        Map<Character, Integer> posMap = new HashMap<>();

        for (int i = 0; i < vars.length; i++) {
            posMap.put((char) vars[i], i);	// cast is safe, since only a-z are allowed as var-names
        }
        // list monomial-strings
        Pattern monomialPattern = Pattern.compile("[+-]?\\d*([a-z](\\^\\d+)?\\*?)*");
        Matcher monomialMatcher = monomialPattern.matcher(poly_string);

        List<String> matches = new ArrayList<>();

        while (monomialMatcher.find()) {
            String s = monomialMatcher.group();

            if (s.equals("")) {
                continue;
            }

            matches.add(s);
        }

        // generate monomials
        Pattern varPattern = Pattern.compile("([a-z])((\\^\\d+)?)\\*?");
        Pattern signCoeffPattern = Pattern.compile("([+-]?)(\\d*)");

        Map<Exponents, Long> monomials = new HashMap<>();

        for (String m : matches) {
            Matcher signCoeffMatcher = signCoeffPattern.matcher(m);

            if (!signCoeffMatcher.find()) {
                throw new IllegalArgumentException("Couldn't find sign / coefficient for monomial");
            }

            long coeff;

            switch (signCoeffMatcher.group(1)) {
                case "":
                case "+":
                    coeff = 1;
                    break;
                case "-":
                    coeff = -1;
                    break;
                default:
                    throw new RuntimeException("Dunno wtf happened here");
            }

            if (signCoeffMatcher.group(2).length() > 0) {
                coeff *= Long.parseLong(signCoeffMatcher.group(2));
            }

            // extract single variable + exponent components
            Matcher varMatcher = varPattern.matcher(m);
            long[] exps = new long[vars.length];
            while (varMatcher.find()) {
                char varc = varMatcher.group(1).charAt(0);

                String expStr = varMatcher.group(2);
                long exp;

                if (expStr.equals("")) {
                    exp = 1;
                } else {
                    exp = Long.parseLong(expStr.substring(1));	// trim ^-sign
                }
                exps[posMap.get(varc)] += exp;
            }

            // place monomial in table
            Exponents e = new Exponents(exps);

            if (monomials.containsKey(e)) {
                monomials.put(e, monomials.get(e) + coeff);
            } else {
                monomials.put(e, coeff);
            }
        }

        // build polynomial
        return new Polynomial(monomials, vars.length);
    }

    public static Polynomial zeroPolynomial(int nVars) {
        return new Polynomial(nVars);
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
            sb.append(Math.abs(coeff)).append( e.toString());
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

    public long content() {
        long res = 0;

        for (long c : monomials.values()) {
            res = ExtendedEuclideanGCDResult.calculateGCD(res, c).gcd;
        }

        return res;
    }

    public Polynomial primitivePolynomial() {
        long c = content();
        Polynomial res = new Polynomial(nVars);

        for (Exponents e : monomials.keySet()) {
            res.monomials.put(e, monomials.get(e) / c);
        }

        return res;
    }

    public long getDegree(int var) {
        return monomials.keySet().stream().max(Comparator.comparing(e -> e.e[var])).
                map(e -> e.e[var]).orElse(0l);
    }

    public long getDegree() {
        return getDegree(0);
    }

    public static class Exponents implements Comparable<Exponents> {

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
                int diff = Long.compare(e(i), o.e(i));
                if (diff != 0) {
                    return diff;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < e.length; i++) {
                sb.append(" * x_").append(i).append("^").append(e(i));
            }
            return sb.toString();
        }
        
        

    }
}
