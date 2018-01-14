package modulargcd;

import PACKAGE_NAME.ExtendedEuclideanGCDResult;
import PACKAGE_NAME.Polynomial;
import PACKAGE_NAME.PrimeGenerator;
import javafx.util.Pair;

public class ModularGCD {

    public static void main(String[] args) {
        // TODO code application logic here
        polynomials();
        primes();
        gcd();
    }

    private static void polynomials() {
        // Example usage of polynomials
        Polynomial poly1 = Polynomial.parse("-15*1^2*2^3*4^5+12*3^2*0^3", 5);
        Polynomial poly2 = Polynomial.parse("15*1^2*2^3*4^5-3*0^8*4^2*2^3", 5);
        System.out.println(poly1);
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
        
        Polynomial p1 = Polynomial.parse("15*0^3*1^3*2^3-2*0^3*2^1", 3);
        Polynomial p2 = Polynomial.parse("3*0^2*1^1*2^3+5*0^1*1^1*2^5", 3);
        Pair<Polynomial, Polynomial> res = p1.div_mod(p2);
        System.out.println(res.getKey()); // Should be -5 * x_0^1 * x_1^2 * x_2^0
        System.out.println(res.getValue()); // Should be -2 * x_0^3 * x_1^0 * x_2^1   -25 * x_0^2 * x_1^3 * x_2^5
    }

    private static void primes() {
        // Example usage of the prime generator
        PrimeGenerator p1 = new PrimeGenerator();
        // Generate 9999 primes
        for (int i = 0; i < 9999; i++) {
            p1.getAsInt();
        }
        // Generate and print the 10000th prime
        System.out.println(p1.getAsInt());
        PrimeGenerator p2 = new PrimeGenerator(5);
        // Generate and print 5 and the 4 primes following it
        for (int i = 0; i < 5; i++) {
            System.out.println(p2.getAsInt());
        }
    }

    private static void gcd() {
        System.out.println();
        ExtendedEuclideanGCDResult e = ExtendedEuclideanGCDResult.calculateGCD(1071, 462);
        System.out.println(e.getGCD()); // Should be 21
        System.out.println(e);
    }

}
