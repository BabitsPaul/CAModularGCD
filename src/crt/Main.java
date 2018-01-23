package crt;

public class Main {
    
    public static void main(String[] args) {
	//u, v and g are 'pointers'
	LongObj u;
	LongObj v;
 	LongObj gcd;

	u = new LongObj();
	v = new LongObj();
	gcd = new LongObj();

	Euclide A = new Euclide();

	A.extended_euclidean_algorithm(u, v, gcd, 264, 198);

	System.out.println("u : "+ u.value + " v : " + v.value + " gcd : " + gcd.value);

//test for chinese rest theorem

 	long m[] = { 12, 5 ,11 };
  	long a[] = { 3, 2, 1 };
 	LongObj x;
	x = new LongObj();

  	System.out.println("Solution for chinese rest theorem 1 yes, 0 no : " + Crt.cra(x,a,m));
  	if(x.value>=0) System.out.println("the solution x is : "+ x.value);
   }
}
