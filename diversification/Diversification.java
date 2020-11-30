package diversification;

import java.util.*;

public class Diversification {
    static int MAX_VAL = 1_000_000_002;

    private boolean possible(List<Integer> opportunityMoments, List<Integer> stockTypes, int k, int value) {
        int maxK = 1<<k;

        int[] combinations = new int[maxK];
        for (int i = 0; i < maxK; i++) {
            combinations[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < opportunityMoments.size(); i++) {
            int dist = opportunityMoments.get(i) - value;
            int type = 1<<(stockTypes.get(i) - 1);
            for (int j = 0; j < maxK; j++) {
                if ((j & type) != 0) continue; // it already contains this bit
                if (combinations[j] <= dist) {
                    int bit = (j | type);
                    combinations[bit] = Math.min(combinations[bit], opportunityMoments.get(i));
                }
            }
            combinations[type] = Math.min(combinations[type], opportunityMoments.get(i));
        }
        return combinations[maxK - 1] != Integer.MAX_VALUE;
    }

    public int maximalTimeRange(int k, List<Integer> opportunityMoments, List<Integer> stockTypes) {
        int lo = 0;
        int hi = MAX_VAL;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (possible(opportunityMoments, stockTypes, k, mid)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
}
