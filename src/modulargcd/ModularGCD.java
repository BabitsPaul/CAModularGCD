package modulargcd;

import PACKAGE_NAME.PrimeGenerator;

public class ModularGCD {

    public static void main(String[] args) {
        // TODO code application logic here
        primes();
    }

    private static void primes() {
        // Example usage of the prime generator
        PrimeGenerator p = new PrimeGenerator();
        // Generate and print the first 1000 primes
        for (int i = 0; i < 10000; i++) {
            System.out.println(p.getAsInt());
        }
    }

}
