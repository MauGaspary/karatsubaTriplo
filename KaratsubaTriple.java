public class KaratsubaTriple {

	public static String[] strCopy(long index, String string) {
		String first = "",
				middle = "",
				last = "";

		if (string.length() % 3 == 2) {
			long actualIndex = string.length() - (2 * index) - 1;
			for (int i = 0; i < actualIndex; i++) {
				first += string.charAt(i);
			}

			for (int i = (int) actualIndex; i < string.length() - index; i++) {
				middle += string.charAt(i);
			}

			for (int i = (int) (string.length() - index); i < string.length(); i++) {
				last += string.charAt(i);
			}

		} else {

			long actualIndex = string.length() - (2 * index);
			for (int i = 0; i < actualIndex; i++) {
				first += string.charAt(i);
			}

			for (int i = (int) actualIndex; i < string.length() - index; i++) {
				middle += string.charAt(i);
			}

			for (int i = (int) (string.length() - index); i < string.length(); i++) {
				last += string.charAt(i);
			}
		}
		return new String[] { first, middle, last };
	}

	public static long power(long x, long y) {
		if (y == 0)
			return 1;
		else {
			long answer = 1;
			for (int i = 1; i <= y; i++) {
				answer *= x;
			}
			return answer;
		}
	}

	public static long karatsuba(long x, long y) {
		if (x < 10 || y < 10) {
			return x * y;
		} else {
			String xString = Integer.toString((int) x),
					yString = Integer.toString((int) y);
				long m = xString.length(),
					n = yString.length(),
					m3 = m / 3,
					n3 = n / 3,
					high1 = Integer.parseInt(strCopy(m3, xString)[0]),
					mid1 = Integer.parseInt(strCopy(m3, xString)[1]),
					low1 = Integer.parseInt(strCopy(m3, xString)[2]),
					high2 = Integer.parseInt(strCopy(n3, yString)[0]),
					mid2 = Integer.parseInt(strCopy(n3, yString)[1]),
					low2 = Integer.parseInt(strCopy(n3, yString)[2]);

					System.out.println(high1 + " high1");
					System.out.println(mid1 + " mid1");
					System.out.println(low1 + " low1");
					System.out.println(high2 + " high2");
					System.out.println(mid2 + " mid2");
					System.out.println(low2 + " low2");
			/*
			long z0 = karatsuba(low1, low2),
			z2 = karatsuba(high1, high2),
			z1 = karatsuba((low1 + high1), (low2 + high2)) - z2 - z0;
			return (z2 * power(10, 2 * m3) + (z1 * power(10, m3)) + z0);
			(za2 ∗ power(10,2 * m3) + za1 ∗ power(10, m3) + za0) ∗ (zb2 ∗ power(10,2 * m3) + zb1 ∗ power(10, m) + zb0)
			*/
			return 1;
		}
	}
	//h1*(h2,m2,l2)+m1*(h2,m2,l2)+l1(h2,m2,l2)
	//z3 * power(10, 3*m3) + z2 * power(10, 2*m3) + (z1 * power(10, m3)) + z0


	public static void main(String[] args) {

		System.out.println(karatsuba(4217, 2123));
		// System.out.println(karatsuba(1234447890, 987654321));
		// System.out.println(karatsuba(237770525, 15123445));
		// System.out.println(karatsuba(1234586789, 987654321));
	}
}