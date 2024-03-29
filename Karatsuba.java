/*
 * The Karatsuba algorithm is a multiplication algorithm developed by Anatolii Alexeevitch Karatsuba in 1960.
 * It operates in O(n^log2(3)) time (~ O(n^1.585)), with n being the number of digits of the numbers we are multiplying together.
 * Standard grade-school multiplication operates in O(n^2) time. Karatsuba's method is asymptotically much faster.
 * Normally, you can choose any base you want, but we will be using base 10 in this algorithm with m varying depending on the length of the inputs.
 * Specific details are included with an example in the comments before the actual method.
 * 
 * @author Ayamin
 * 
 */

public class Karatsuba {
	
	// Takes two integers and returns the maximum of them
	public static int max(int x, int y) {
		return (x>y)? x:y;
	}
	
	// Takes a string and an index. 
	// The index in this case is the "m". It will count backwards from the last (least significant) digit and split the string there.
	// It will return a 2-element array of the split string.
	// For example: Given 12345 as the string and 2 as the index, it will split the string into the string array ["123", "45"].
	// This is so the 123 can be written as 123 * 10^m, with m = 2 the index.
	public static String[] strCopy(long index, String string) {
		String	first = "",
				last = "";
		long actualIndex = string.length() - index;
		for (int i = 0; i<actualIndex; i++) {
			first+=string.charAt(i);
		}
		for (int i = (int)actualIndex; i<string.length(); i++) {
			last+=string.charAt(i);
		}
		return new String[] {first, last};
	}
	
	// An exponent function. Works the same way as Math.pow, but with 64bit integers instead of double precision floats.
	public static long power(long x, long y) {
		if (y == 0)
			return 1;
		else {
			long answer = 1;
			for (int i = 1; i<=y; i++) {
				answer *= x;
			}
			return answer;
		}
	}
	
	/*
	 * Take two numbers, x and y.
	 * Example: 12345 and 6789.
	 * Find a base b and power m to separate it into.
	 * We'll pick base = 10, and m to be half the length of the digits of the numbers in this implementation of the algorithm.
	 * 	In this case, m will be 2, so 10^2 = 100. We will split the 2 numbers using this multiplier.
	 * The form we want is:
	 * 
	 * x = x1*b^m + x0
	 * y = y1*b^m + y0
	 * ----------
	 * x = x2 ∗ b^m2 + x1 ∗ b^m1 + x0
	 * y = y2 ∗ b^m2 + y1 ∗ b^m1 + y0
	 * ----------
	 * Using the above example,
	 * x1 = 123
	 * x0 = 45
	 * ----------
	 * y1 = 67
	 * y2 = 89
	 * ----------
	 * b = 10 and m = 2
	 * ----------
	 * Thus:
	 * 12345 = 123 * 10^2  +  45
	 * 6789 =   67 * 10^2  +  89
	 * ----------
	 * 2342444 = 234 * 10^4 + 24 * 10^2 + 44
	 * 866868  =  86 * 10^4 + 68 * 10^2 + 68
	 * 
	 * a = a1 ∗ 10e1 + a2 ∗ 10e2 + a3
	 * b = b1 ∗ 10e1 + b2 ∗ 10e2 + b3
	 * ----------
	 * 
	 * 
	 * The recursive algorithm is as follows:
	 * 
	 * If x<10 or y<10, return x*y. Single digit multiplication is the base case.
	 * Otherwise:
	 * Let z3 = karatsuba(x2,y2)
	 * Let z2 = karatsuba(x1, y1). x1 and y1 are the high significant digits, and are the local variables "high".
	 * Let z0 = karatsuba(x0, y0). x0 and y0 are the least significant digits, and are the local variables "low".
	 * Let z1 = karatsuba(x1+y0, x0+y1) - z0 - z2.
	 * 
	 * And the result is the following sum:
	 * 
	 * (z2 * power(10, 2*m2) + (z1 * power(10, m2)) + z0)
	 * z3 * power(10, 3*m3) + z2 * power(10, 2*m3) + (z1 * power(10, m3)) + z0
	 *  z2 *   b^2m	         +	z1 *   b^m	        + z0
	 * a ∗ b = (za2 ∗ b^2m + za1 ∗ b^m + za0) ∗ (zb2 ∗ b^2m + zb1 ∗ b^m + zb0)
	 * 
	 * Let z2 = karatsuba(x2, y2). x1 and y1 are the most significant digits, and are the local variables "high".
	 * Let z0 = karatsuba(x1, y1). x0 and y0 are the mid significant digits, and are the local variables "mid".
	 * 
	 * Let z1 = karatsuba(x1+y0, x0+y1) - z0 - z2.
	 * 
	 * @param x The multiplicand.
	 * @param y The multiplier.
	 * @return The product.
	 */
	
	public static long karatsuba(long x, long y) {
		// Base case: single digit multiplication
		if (x<10 || y<10) {
			return x * y;
		}
		// Recursive case: Decompose the problem by splitting the integers and applying the algorithm on the parts.
		else {
			// Convert the numbers to strings so we can compute the # of digits of each number.
			// Note: We could also use floor(log10(n) + 1) to compute the #digits, but remember that we need to split the numbers too.
			String xString = Integer.toString((int)x);
			String yString = Integer.toString((int)y);
			// Local variables
			long 	m = max(xString.length(), yString.length()), // the maximum # of digits
					m2 = m/2, // the middle; if the number is odd, it will floor the fraction
					high1 = Integer.parseInt(strCopy(m2, xString)[0]), // the most significant digits. this is the scalar multiplier for b^m2
					low1 = Integer.parseInt(strCopy(m2, xString)[1]), // the least significant digits. this is what is added on to high1*b^m2
					high2 = Integer.parseInt(strCopy(m2, yString)[0]), // same for y
					low2 = Integer.parseInt(strCopy(m2, yString)[1]), // same for y
					// Three recursive calls
					z0 = karatsuba(low1, low2), // z0 = x0y0
					z2 = karatsuba(high1, high2), // z2 = x1y1
					z1 = karatsuba((low1 + high1), (low2 + high2)) - z2 - z0; // z1 = (x0 + y1)*(x1 + y0) - z2 - z0, courtesy of Karatsuba

			return (z2 * power(10, 2*m2) + (z1 * power(10, m2)) + z0);
		}
	}

	public static void main(String[] args) {
		
		System.out.println(karatsuba(12, 34));
		//System.out.println(karatsuba(12345, 6789));
		// System.out.println(karatsuba(2358925, 1259174));
		//System.out.println(karatsuba(1234586789, 987654321));
	}
}