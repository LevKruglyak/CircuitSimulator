package util;

import java.util.List;

public class Util {
	public static boolean[] packArray(boolean[] data, int size) {
		boolean[] out = new boolean[size];
		for (int i = 0; i < data.length; i++) {
			if (i < size) {
				out[i] = data[i];
			}
		}

		return out;
	}

	public static boolean OR1(boolean[] A) {
		boolean C = false;
		for (int i = 0; i < A.length; i++) {
			C = C || A[i];
		}
		return C;
	}

	public static boolean AND1(boolean[] A) {
		boolean C = true;
		for (int i = 0; i < A.length; i++) {
			C = C && A[i];
		}
		return C;
	}

	public static boolean[] OR(boolean[] A, boolean[] B) {
		boolean[] C = new boolean[A.length];
		for (int i = 0; i < A.length; i++) {
			C[i] = A[i] || B[i];
		}
		return C;
	}

	public static boolean[] AND(boolean[] A, boolean[] B) {
		boolean[] C = new boolean[A.length];
		for (int i = 0; i < A.length; i++) {
			C[i] = A[i] && B[i];
		}
		return C;
	}

	public static boolean[] NOT(boolean[] A) {
		boolean[] C = new boolean[A.length];
		for (int i = 0; i < A.length; i++) {
			C[i] = !A[i];
		}
		return C;
	}

	public static boolean[] XOR(boolean[] A, boolean[] B) {
		boolean[] C = new boolean[A.length];
		for (int i = 0; i < A.length; i++) {
			C[i] = A[i] ^ B[i];
		}
		return C;
	}

	public static long toDecimal(boolean[] binary) {
		long num = 0;
		for (int i = 0; i < binary.length; i++) {
			if (binary[i])
				num += (long) (Math.pow(2, i));
		}
		return num;
	}

	public static String toString(boolean[] binary) {
		String out = "";
		for (boolean bl : binary) {
			out += bl ? "1" : "0";
		}
		return out;
	}

	public static String toString2D(boolean[][] binary) {
		String out = "";
		for (int i = 0; i < binary.length; i++) {
			out += toDecimal(binary[i]) + "-";
		}
		if (out.equals("")) {
			return " ";
		}
		return out;
	}

	public static boolean[][] toBinary2D(String str, int length, int size) {
		boolean[][] out = new boolean[length][size];
		if (str.equals(" ")) {
			return out;
		}
		String[] data = str.split("-");
		for (int i = 0; i < length; i++) {
			out[i] = packArray(toBinary(Long.parseLong(data[i])), size);
		}

		return out;
	}

	public static boolean[] toBinaryArray(String str) {
		boolean[] out = new boolean[str.length()];
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).equals("1")) {
				out[i] = true;
			} else {
				out[i] = false;
			}
		}
		return out;
	}

	public static int getDigit(long num, int base, int index) {
		long basePow = (long) Math.pow(base, index);
		return (int) ((num % (base * basePow)) / basePow);
	}

	public static int[] getDigits(long num, int base) {
		int[] digits = new int[numDigits(num, base)];
		for (int i = 0; i < digits.length; i++) {
			digits[i] = getDigit(num, base, i);
		}
		return digits;
	}

	public static int numDigits(long num, int base) {
		int digs = 0;
		num = Math.abs(num);
		while (num >= base) {
			num /= base;
			digs++;
		}
		return digs + 1;
	}

	public static int[] packArray(int[] data, int size) {
		int[] out = new int[size];
		for (int i = 0; i < data.length; i++) {
			if (i < size) {
				out[i] = data[i];
			}
		}

		return out;
	}

	public static boolean[] toBinary(int[] arr, int startIndex) {
		boolean[] bin = new boolean[arr.length-startIndex];
		for (int i = startIndex; i < arr.length; i++) {
			if (arr[i] == 1) {
				bin[i-startIndex] = true;
			}
		}
		
		return bin;
	}
	
	public static boolean[] toBinary(long num) {
		int[] digits = getDigits(num, 2);
		boolean[] dig = new boolean[digits.length];

		for (int i = 0; i < digits.length; i++) {
			dig[i] = digits[i] == 1;
		}
		return dig;
	}

	public static boolean XNOR1(boolean[] a, boolean[] b) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	public static String toString(List<int[]> cases) {
		String out = "";
		for (int[] arr : cases) {
			for (int i = 0; i < arr.length; i++) {
				out += arr[i];
			}
			out += "-";
		}
		return out;
	}

	public static int[] toIntArray(String line) {
		int[] out = new int[line.length()];
		for (int i = 0; i < line.length(); i++) {
			out[i] = Integer.parseInt(line.substring(i, i+1));
		}
		return out;
	}

	public static boolean[] reverse(boolean[] bs) {
		boolean[] sb = new boolean[bs.length];
		for (int i = 0; i < sb.length; i++) {
			sb[sb.length-i-1] = bs[i];
		}
		return sb;
	}
}
