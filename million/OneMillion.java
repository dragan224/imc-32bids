package million;

import java.util.List;

public class OneMillion {
    private static int MAX_VAL = 1001;

    public double estimateProbability(List<Double> probabilities, List<Integer> profits) {
        double[] dp = new double[MAX_VAL];
        dp[0] = 1;

        for (int j = 0; j < probabilities.size(); j++) {
            double prob = probabilities.get(j);
            int profit = profits.get(j) / 1000;

            double[] no = new double[MAX_VAL];
            for (int i = 0; i < MAX_VAL; i++) {
                no[i] = dp[i] * (1. - prob);
            }

            double[] yes = new double[MAX_VAL];
            for (int i = 0; i < MAX_VAL; i++) {
                int newProfit = i + profit;
                if (newProfit >= MAX_VAL) {
                    newProfit = MAX_VAL - 1;
                }

                yes[newProfit] += dp[i] * prob;
            }

            for (int i = 0; i < MAX_VAL; i++) {
                dp[i] = no[i] + yes[i];
            }
        }

        return dp[MAX_VAL - 1];
    }
}
