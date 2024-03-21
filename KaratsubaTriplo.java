public class KaratsubaTriplo {

	public static int max(int x, int y) {
		return (x > y) ? x : y;
	}

	public static String[] strCopy(int index, String string) {
		String first = "",
				last = "";
		int actualIndex = string.length() - index;
		for (int i = 0; i < actualIndex; i++) {
			first += string.charAt(i);
		}
		for (int i = (int) actualIndex; i < string.length(); i++) {
			last += string.charAt(i);
		}
		return new String[] { first, last };
	}

	public static int power(int x, int y) {
		if (y == 0)
			return 1;
		else {
			int answer = 1;
			for (int i = 1; i <= y; i++) {
				answer *= x;
			}
			return answer;
		}
	}

	// public static String karatsuba(String x, String y, String z) {
	// return null;

	// }

	public static String karatsuba(String x, String y) {
		// Base case: single digit multiplication
		if (x.length() == 1 || y.length() == 1) {
			int prod = Integer.parseInt(x) * Integer.parseInt(y);
			return String.valueOf(prod);

		}

		else {
			int m, m2;
			m = max(x.length(), y.length());
			m2 = m / 2;
			String high1 = strCopy(m2, x)[0];
			String low1 = strCopy(m2, x)[1];
			String high2 = strCopy(m2, y)[0];
			String low2 = strCopy(m2, y)[1];

			String z0 = karatsuba(low1, low2);
			String z2 = karatsuba(high1, high2);
			String sumLowHigh1 = addStrings(low1, high1);
			String sumLowHigh2 = addStrings(low2, high2);

			String z1 = subtractStrings(subtractStrings(karatsuba(sumLowHigh1, sumLowHigh2), z2), z0);

			return addStrings(addStrings(shiftLeft(z2, 2 * m2), shiftLeft(z1, m2)), z0);
		}
	}

	public static String addStrings(String num1, String num2) {
		StringBuilder result = new StringBuilder();
		int carry = 0;
		int i = num1.length() - 1;
		int j = num2.length() - 1;

		while (i >= 0 || j >= 0 || carry > 0) {
			int sum = carry;
			if (i >= 0) {
				sum += num1.charAt(i--) - '0';
			}
			if (j >= 0) {
				sum += num2.charAt(j--) - '0';
			}
			carry = sum / 10;
			result.insert(0, sum % 10);
		}

		return result.toString();
	}

	public static String subtractStrings(String num1, String num2) {
		StringBuilder result = new StringBuilder();
		int borrow = 0;
		int i = num1.length() - 1;
		int j = num2.length() - 1;

		while (i >= 0 || j >= 0) {
			int diff = borrow;
			if (i >= 0) {
				diff += num1.charAt(i--) - '0';
			}
			if (j >= 0) {
				diff -= num2.charAt(j--) - '0';
			}
			if (diff < 0) {
				diff += 10;
				borrow = -1;
			} else {
				borrow = 0;
			}
			result.insert(0, diff);
		}

		return result.toString();
	}

	public static String shiftLeft(String num, int digits) {
		StringBuilder result = new StringBuilder(num);
		for (int i = 0; i < digits; i++) {
			result.append('0');
		}
		return result.toString();
	}

	public static void main(String[] args) {

		System.out.println(karatsuba("999999999", "9000000000"));
		// System.out.println(karatsuba("12345", "6789"));
		System.out.println(karatsuba("12432134341245674745675476","7054920058988836008343024"));
	}
}