package modulargcd;

import PACKAGE_NAME.Polynomial;
import PACKAGE_NAME.PrimeGenerator;

public class ModularGCD {

    public static void main(String[] args) {
        // TODO code application logic here
        polynomials();
        primes();
    }

    private static void polynomials() {
        // Example usage of polynomials
        Polynomial poly1 = Polynomial.parse("-15*1^2*2^3*4^5+12*3^2*0^3", 5);
        Polynomial poly2 = Polynomial.parse("15*1^2*2^3*4^5-12*4^2*2^3", 5);
        System.out.println(poly1);
        Polynomial twicePoly1 = poly1.add(poly1);
        System.out.println(twicePoly1);
        Polynomial polySum = poly1.add(poly2);
        System.out.println(polySum);
        Polynomial polyProd = twicePoly1.add(polySum);
        System.out.println(polyProd);
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

}
