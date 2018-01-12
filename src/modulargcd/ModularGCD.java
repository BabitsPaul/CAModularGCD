/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulargcd;

import PACKAGE_NAME.PrimeGenerator;

/**
 *
 * @author William
 */
public class ModularGCD {

    /**
     * @param args the command line arguments
     */
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
