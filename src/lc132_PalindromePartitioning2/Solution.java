package lc132_PalindromePartitioning2;
/*
Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

Example:

Input: "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
 */
class Solution {

    public int minCut(String s) {
        int n = s.length();
        int[] pls = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 1; i-j >= 0 && i+j < n && s.charAt(i-j) == s.charAt(i+j); j++) {
                pls[i-j] = Math.max(pls[i-j], j*2);
            }
            for (int j = 0; i-j >=0 && i+j+1 <n && s.charAt(i-j) == s.charAt(i+j+1); j++) {
                pls[i-j] = Math.max(pls[i-j], j*2+1);
            }
        }

        int[] dp = new int[s.length()];
        for (int i = 1; i < n; i++) dp[i] = i;

        dp[pls[0]] = dp[0];
        for (int i = 1; i < n; i++) {
            int t = dp[i-1] + 1;
            if (dp[i] > t) dp[i] = t;
            if (dp[i + pls[i]] > t) dp[i + pls[i]] = t;
        }
        return dp[n-1];
    }

    public int minCut_old(String s) {
        int n = s.length();
        if (n < 2) return 0;
        if (n == 2) {
            if (s.charAt(0) == s.charAt(1)) {
                return 0;
            } else {
                return 1;
            }
        }
        int[][] dp = new int[n][n];
        for (int i = 0; i+1 < n; i++) {
            if (s.charAt(i) != s.charAt(i+1)) dp[1][i]++;
        }
        for (int i = 0; i+2 < n; i++) {
            if (s.charAt(i) != s.charAt(i+2)) {
                dp[2][i] = dp[1][i] + dp[1][i+1];
            }
        }
        for (int i = 3; i < n; i++) {
            for (int j = 0; j < n-i; j++) {
                if (s.charAt(j) == s.charAt(j+i) && dp[i-2][j+1] == 0) {
                    dp[i][j] = 0;
                } else {
                    int min = 1 + dp[i-1][j+1];
                    for (int k = 1; k < i; k++) {
                        int opt = 1 + dp[k][j] + dp[i-k-1][j+k+1];
                        if (opt < min) min = opt;
                    }
                    dp[i][j] = min;
                }
            }
        }
        return dp[n-1][0];


    }
}