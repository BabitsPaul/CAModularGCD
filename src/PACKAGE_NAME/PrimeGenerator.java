package PACKAGE_NAME;

import java.util.function.IntSupplier;

public class PrimeGenerator implements IntSupplier {

    /**
     * Truth values in this are inverted: true means not a prime
     */
    private boolean[] primes;

    /**
     * The current index for iterating through the array
     */
    private int current;

    /**
     *
     */
    public PrimeGenerator() {
        this.primes = new boolean[8];
        current = 2;
        sievePrimes();
    }

    /**
     * Returns the next prime
     */
    @Override
    public int getAsInt() {
        while (current < primes.length) {
            if (!primes[current]) {
                return current++;
            } else {
                current++;
            }
        }
        primes = new boolean[primes.length * 2];
        sievePrimes();
        return getAsInt();
    }

    /**
     * Sieve of Eratosthenes
     */
    private void sievePrimes() {
        primes[0] = true;
        primes[1] = true;
        int p = 2;
        loop:
        while (p <= (int) Math.sqrt(primes.length) + 1) {
            for (int n = 2 * p; n < primes.length; n += p) {
                primes[n] = true;
            }
            do {
                p++;
            } while (primes[p]);
        }
    }

}
