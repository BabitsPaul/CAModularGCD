package crt;

public class Euclide {
	  //private long a;
	 // private long b;
	  //private
	//public Euclide(long a, long b, 


	public static void extended_euclidean_algorithm(LongObj u, LongObj v, LongObj gcd, long a, long b){

	  long u1;
	  long u2;
	  long u3;
	  long v1;
	  long v2;
	  long v3;
	  long t1;
	  long t2;
	  long t3;
	  long q;		
	  u1 = 1;
	  u2 = 0;
	  u3 = a;
	  v1 = 0;
	  v2 = 1;
	  v3 = b;

	  while (v3!=0) {
	    q = u3 / v3;
	    t1 = u1 - q*v1;
	    t2 = u2 - q*v2;
	    t3 = u3 - q*v3;

	    u1 = v1;
	    u2 = v2;
	    u3 = v3;

	    v1 = t1;
	    v2 = t2;
	    v3 = t3;
	  }
	  u.value = u1;
	  v.value = u2;
	  gcd.value = u3;
	}
}
