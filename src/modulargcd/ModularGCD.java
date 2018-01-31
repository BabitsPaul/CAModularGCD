package modulargcd;

import impl.ExtendedEuclideanGCDResult;
import impl.ModGCD;
import impl.Polynomial;
import arithm.PrimeGenerator;
import javafx.util.Pair;

public class ModularGCD {

    public static void main(String[] args) {
        // TODO code application logic here
        // polynomials();
        // primes();
        // gcd();

		/*
        Polynomial pt1 = Polynomial.parse("11x^4 - 7x^3 + 5x^2 - 3x^1+8", 1);
        Polynomial pt2 = Polynomial.parse("1x^1+1", 1);
        System.out.println(pt1);
        System.out.println(pt2);
        Pair<Polynomial, Polynomial> div_mod = pt1.div_mod(pt2);
        System.out.println(div_mod.getKey());
        System.out.println(div_mod.getValue());
        System.out.println(pt1);
        System.out.println(div_mod.getKey().mul(pt2));
        System.out.println("--------");
        */

        Polynomial a = Polynomial.parse("2x^6 - 13x^5 + 20x^4 + 12x^3 - 20x^2 - 15x - 18", 1);
        Polynomial b = Polynomial.parse("2x^6 + x^5 - 14x^4 - 11x^3 + 22x^2  + 28x + 8", 1);

        System.out.println(ModGCD.gcd_modulo(a, b, 5));
		System.out.println(ModGCD.gcd_modulo(a, b, 7));
        System.out.println(ModGCD.gcd_modulo(a, b, 11));
		System.out.println(ModGCD.gcd_modulo(a, b, 13));

		System.out.println(Polynomial.parse("2x^3 + 5x^2 - 3", 1).toPositiveCoefficients(13).normalizeByLP(13));

		/*
        a = Polynomial.parse("4x^5 - 13x^3 + 7x^2 + 8", 1);
        b = Polynomial.parse("2x^6 + 9x^4 + 8x^2 + 12x + 9", 1);

        Polynomial tmp = ModGCD.gcd_modulo(a, b, 11);
        System.out.println(tmp);

        a = a.div(tmp);
        b = b.div(tmp);

        System.out.println(a.mul(tmp));
        System.out.println(b.mul(tmp));

        // System.out.println(ModGCD.mod_gcd(a, b));
        */
    }

    private static void polynomials() {
        // Example usage of polynomials
        Polynomial poly1 = Polynomial.parse("-15*x^2*y^3*z^5+12*3^2*u^3", 5);
        Polynomial poly2 = Polynomial.parse("15*x^2*y^3*z^5-3*u^8*z^2*y^3", 5);
        System.out.println(poly1.toString());
        Polynomial twicePoly1 = poly1.add(poly1);
        System.out.println(twicePoly1);
        Polynomial polySum = poly1.add(poly2);
        System.out.println(polySum);
        Polynomial polyProd = twicePoly1.mul(polySum);
        System.out.println(polyProd);
        Pair<Polynomial, Polynomial> divRes = polyProd.div_mod(poly1);
        System.out.println(divRes.getKey()); // The result of the divsion
        System.out.println(divRes.getValue()); // The remainder
        Polynomial zero1 = twicePoly1.sub(twicePoly1);
        System.out.println(zero1); // Empty line
        Polynomial zero2 = poly2.mul(0);
        System.out.println(zero2); // Empty line

        Polynomial p1 = Polynomial.parse("15*u^3*x^3*y^3-2*u^3*y^1", 3);
        Polynomial p2 = Polynomial.parse("3*u^2*x^1*y^3+5*u^1*x^1*y^5", 3);
        Pair<Polynomial, Polynomial> res = p1.div_mod(p2);
        System.out.println(res.getKey()); // Should be -5 * x_0^1 * x_1^2 * x_2^0
        System.out.println(res.getValue()); // Should be -2 * x_0^3 * x_1^0 * x_2^1   -25 * x_0^2 * x_1^3 * x_2^5
    }

    private static void primes() {
        // Example usage of the prime generator
        PrimeGenerator p1 = new PrimeGenerator();
        // Generate 9999 primes
        for (int i = 0; i < 9999; i++) {
            p1.next();
        }
        // Generate and print the 10000th prime
        System.out.println(p1.next());
        PrimeGenerator p2 = new PrimeGenerator(5);
        // Generate and print 5 and the 4 primes following it
        for (int i = 0; i < 5; i++) {
            System.out.println(p2.next());
        }
    }

    private static void gcd() {
        System.out.println();
        ExtendedEuclideanGCDResult e = ExtendedEuclideanGCDResult.calculateGCD(1071, 462);
        System.out.println(e.getGCD()); // Should be 21
        System.out.println(e);
    }

}
