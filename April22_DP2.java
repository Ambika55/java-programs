package DP;

/**
 * @author Garima Chhikara
 * @email garima.chhikara@codingblocks.com
 * @date 22-Apr-2018
 */

public class April22_DP2 {

	public static void main(String[] args) {

		int egg = 20;
		int floor = 360;
		System.out.println(eggDropTD(egg, floor, new int[egg + 1][floor + 1]));
		System.out.println(eggDropBU(egg, floor));

		String str = "ababbbabbababacdbdccc";
		System.out.println(palindromePartitionTD(str, 0, str.length() - 1, new int[str.length() + 1][str.length() + 1]));
		System.out.println(palindromePartitionBU(str));

		// int[] arr = { 40, 60, 20 };
		int[] arr = { 41, 67, 34, 0, 69, 24, 78, 58, 62, 64 };
		System.out.println(mixturesTD(arr, 0, arr.length - 1));
		System.out.println(mixturesBU(arr));

		int[] price = { 0, -1, 33, -1, 176, -1, 46, -1, 120, -1, 300 };
		System.out.println(minimumMoneyBU(price));

	}

	public static int eggDropTD(int egg, int floor, int[][] strg) {

		if (floor == 0 || floor == 1) {
			return floor;
		}

		if (egg == 1) {
			return floor;
		}

		if (strg[egg][floor] != 0) {
			return strg[egg][floor];
		}
		int min = Integer.MAX_VALUE;

		for (int i = 1; i <= floor; i++) {

			int eggbreak = eggDropTD(egg - 1, i - 1, strg);
			int eggdoesntbreak = eggDropTD(egg, floor - i, strg);

			int ans = Math.max(eggbreak, eggdoesntbreak);

			if (ans < min) {
				min = ans;
			}
		}

		strg[egg][floor] = min + 1;
		return min + 1;

	}

	public static int eggDropBU(int egg, int floor) {

		int[][] strg = new int[egg + 1][floor + 1];

		for (int row = 0; row < strg.length; row++) {
			strg[row][0] = 0;
			strg[row][1] = 1;
		}

		for (int col = 0; col < strg[0].length; col++) {
			strg[1][col] = col;
		}

		for (int e = 2; e < strg.length; e++) {

			for (int f = 2; f < strg[0].length; f++) {

				int min = Integer.MAX_VALUE;

				for (int i = 1; i <= f; i++) {

					int eggbreak = strg[e - 1][i - 1];
					int eggdoesntbreak = strg[e][f - i];

					int ans = Math.max(eggbreak, eggdoesntbreak);

					if (ans < min) {
						min = ans;
					}
				}

				strg[e][f] = min + 1;

			}
		}

		return strg[egg][floor];

	}

	public static boolean isPalindrome(String str) {

		int left = 0;
		int right = str.length() - 1;

		while (left < right) {

			if (str.charAt(left) != str.charAt(right)) {
				return false;
			}

			left++;
			right--;

		}

		return true;
	}

	public static int palindromePartitionTD(String str, int si, int ei, int[][] strg) {

		String ss = str.substring(si, ei + 1);
		if (isPalindrome(ss)) {
			return 0;
		}

		if (strg[si][ei] != 0) {
			return strg[si][ei];
		}

		int min = Integer.MAX_VALUE;

		for (int k = si; k < ei; k++) {

			int fc = palindromePartitionTD(str, si, k, strg);
			int sc = palindromePartitionTD(str, k + 1, ei, strg);
			int ans = fc + sc;

			if (ans < min) {
				min = ans;
			}
		}

		strg[si][ei] = min + 1;
		return min + 1;
	}

	public static int palindromePartitionBU(String str) {

		int n = str.length();
		int[][] strg = new int[n][n];

		for (int slide = 1; slide <= n - 1; slide++) {

			for (int si = 0; si <= n - slide - 1; si++) {

				int ei = si + slide;

				if (isPalindrome(str.substring(si, ei + 1))) {
					strg[si][ei] = 0;
				} else {
					int min = Integer.MAX_VALUE;

					for (int k = si; k < ei; k++) {

						int fc = strg[si][k];
						int sc = strg[k + 1][ei];
						int ans = fc + sc;

						if (ans < min) {
							min = ans;
						}
					}

					strg[si][ei] = min + 1;
				}

			}
		}

		// for (int[] val : strg) {
		// for (int val1 : val) {
		// System.out.print(val1 + " ");
		// }
		// System.out.println();
		// }
		return strg[0][n - 1];

	}

	public static int color(int[] arr, int si, int ei) {

		int sum = 0;
		for (int i = si; i <= ei; i++) {
			sum += arr[i];
		}
		return sum % 100;
	}

	public static int mixturesTD(int[] arr, int si, int ei) {

		if (si == ei) {
			return 0;
		}

		int min = Integer.MAX_VALUE;

		for (int k = si; k < ei; k++) {

			int fc = mixturesTD(arr, si, k);
			int sc = mixturesTD(arr, k + 1, ei);

			int sw = color(arr, si, k) * color(arr, k + 1, ei);

			int ta = fc + sc + sw;

			if (ta < min) {
				min = ta;
			}
		}

		return min;
	}

	public static int mixturesBU(int[] arr) {

		int n = arr.length;
		int[][] strg = new int[n][n];

		for (int slide = 1; slide <= n - 1; slide++) {

			for (int si = 0; si <= n - slide - 1; si++) {

				int ei = si + slide;

				int min = Integer.MAX_VALUE;

				for (int k = si; k < ei; k++) {

					int fc = strg[si][k];
					int sc = strg[k + 1][ei];

					int sw = color(arr, si, k) * color(arr, k + 1, ei);

					int ta = fc + sc + sw;

					if (ta < min) {
						min = ta;
					}
				}

				strg[si][ei] = min;

			}
		}

		return strg[0][n - 1];

	}

	public static int minimumMoneyBU(int[] price) {

		int[] strg = new int[price.length];

		strg[0] = 0;

		if (price[1] == -1) {
			strg[1] = Integer.MAX_VALUE;
		} else {
			strg[1] = price[1];
		}

		for (int i = 2; i < price.length; i++) {

			int min;

			if (price[i] == -1) {
				min = Integer.MAX_VALUE;
			} else {
				min = price[i];
			}

			int left = 1;
			int right = i - 1;

			while (left <= right) {

				int lc = strg[left];
				int rc = strg[right];

				int tw;
				if (lc != Integer.MAX_VALUE && rc != Integer.MAX_VALUE) {

					tw = lc + rc;

					if (tw < min) {
						min = tw;
					}
				}

				left++;
				right--;
			}

			strg[i] = min;
		}

		if (strg[strg.length - 1] == Integer.MAX_VALUE) {
			return -1;
		} else {
			return strg[strg.length - 1];
		}
	}

}
