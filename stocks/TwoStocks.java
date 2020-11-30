package stocks;

import java.util.ArrayList;
import java.util.List;

public class TwoStocks {
    public int calculateProfit(List<Integer> firstStockPrices, List<Integer> secondStockPrices) {
        firstStockPrices = duplicate(firstStockPrices);
        secondStockPrices = duplicate(secondStockPrices);
        int[] profit1 = new int[firstStockPrices.size() + 1];
        int[] profit2 = new int[secondStockPrices.size() + 1];

        int max1 = firstStockPrices.get(0);
        int max2 = secondStockPrices.get(0);
        for (int i = 2; i < profit1.length; i++) {
            int sale1 = firstStockPrices.get(i - 1) - max1;
            int sale2 = secondStockPrices.get(i - 1) - max2;

            int maxPrev = Math.max(profit1[i - 1], profit2[i - 1]);
            profit1[i] = Math.max(maxPrev, sale1);
            profit2[i] = Math.max(maxPrev, sale2);

            max1 = Math.min(max1, firstStockPrices.get(i - 1) - maxPrev);
            max2 = Math.min(max2, secondStockPrices.get(i - 1) - maxPrev);
        }

        return Math.max(profit1[profit1.length - 1], profit2[profit2.length - 1]);
    }

    private List<Integer> duplicate(List<Integer> input) {
        List<Integer> output = new ArrayList<>();
        for (Integer in: input) {
            output.add(in);
            output.add(in);
        }
        return output;
    }
}

