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
     * Generates all primes between min (inclusive) and
     * Integer.Integer.MAX_VALUE
     *
     * @param min a minimum number such that all generated primes are equal or
     * bigger (e.g. 3 if only odd primes should be generated). Need not be a
     * prime
     */
    public PrimeGenerator(int min) {
        this.current = Math.max(2, min);
        this.primes = new boolean[Math.max(8, (current * 3) / 2)];
        sievePrimes();
    }

    /**
     * Generates all primes between 2 (inclusive) and Integer.Integer.MAX_VALUE
     */
    public PrimeGenerator() {
        this(2);
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
        int p = 2;
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
