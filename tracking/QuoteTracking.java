package tracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuoteTracking {
    public int countTraders(List<Integer> volumes) {
        Set<Integer> primeDivisors = new HashSet<>();

        for (Integer volume: volumes) {
            for (int divisor = 2; divisor <= volume; divisor++) {
                if (volume % divisor == 0 && isPrime(divisor)) {
                    primeDivisors.add(divisor);
                }
            }
        }

        int minSolution = Integer.MAX_VALUE;
        Integer[] primeDivisorsArray = new Integer[primeDivisors.size()];
        primeDivisors.toArray(primeDivisorsArray);

        for (int i = 0; i < 1<<primeDivisorsArray.length; i++) {
            List<Integer> divisors = new ArrayList<>();
            for (int j = 0; j < primeDivisorsArray.length; j++) {
                if ((i & 1<<j) != 0) {
                    divisors.add(primeDivisorsArray[j]);
                }
            }

            if (canDivide(volumes, divisors)) {
                minSolution = Math.min(minSolution, divisors.size());
            }
        }
        return minSolution;
    }

    private boolean canDivide(List<Integer> volumes, List<Integer> divisors) {
        for (Integer volume: volumes) {
            boolean found = false;
            for (Integer divisor: divisors) {
                if (volume % divisor == 0) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    private boolean isPrime(int number) {
        for (int divisor = 2; divisor < number; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
