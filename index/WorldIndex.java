package index;

import java.util.*;

public class WorldIndex {

    private static final long MAX_TIME = 4444;

    public String makeName(List<String> inputWithDuplicates) {
        long start = System.currentTimeMillis();
        List<String> input = cleanInput(inputWithDuplicates);

        String solution = "";
        while (System.currentTimeMillis() - start < MAX_TIME) {
            String candidateSolution = new Optimizer(combine(new ArrayList<>(input))).optimize(input, start);
            if (solution.isEmpty() || candidateSolution.length() < solution.length()) {
                solution = candidateSolution;
            }
            Collections.shuffle(input);
        }

        return solution;
    }

    private List<String> cleanInput(List<String> inputWithDuplicates) {
        Set<String> inputWithoutDuplicates = new HashSet<>(inputWithDuplicates);
        Set<String> input = new HashSet<>();
        Map<String, Integer> inDegree = new HashMap<>();
        for (String word1: inputWithoutDuplicates) {
            for (String word2: inputWithDuplicates) {
                if (word1.equals(word2)) continue;
                if (word1.contains(word2)) {
                    inDegree.put(word2, inDegree.getOrDefault(word2, 0) + 1);
                }
                if (word2.contains(word1)) {
                    inDegree.put(word1, inDegree.getOrDefault(word1, 0) + 1);
                }
            }
        }
        for (String word: inputWithDuplicates) {
            if (!inDegree.containsKey(word)) {
                input.add(word);
            }
        }

        return new ArrayList<>(input);
    }

    private String combine(List<String> input) {

        while (input.size() > 1) {
            int from = 0;
            int to = input.size() - 1;
            String bestCombination = input.get(from) + input.get(to);

            int maxPrefix = -1;
            for (int i = 0; i < input.size(); i++) {
                for (int j = 0; j < input.size(); j++) {
                    if (i == j) continue;

                    String combination = tryCombine(input.get(i), input.get(j));
                    int prefix = input.get(i).length() + input.get(j).length() - combination.length();

                    if (prefix > maxPrefix) {
                        maxPrefix = prefix;
                        bestCombination = combination;
                        from = i;
                        to = j;
                    }
                }
            }

            input.set(from, bestCombination);
            input.remove(to);
        }

        return input.get(0);
    }

    private String tryCombine(String from, String to) {
        int prefix = 0;

        for (int i = 1; i <= Math.min(from.length(), to.length()); i++) {
            if (from.substring(from.length() - i).equals(to.substring(0, i))) {
                prefix = i;
            }
        }

        return from + to.substring(prefix);
    }

    private class Optimizer {
        private String solution;
        private boolean found;

        private Optimizer(String solution) {
            this.solution = solution;
            this.found = false;
        }

        private String optimize(List<String> input, long start) {
            while (System.currentTimeMillis() - start < MAX_TIME) {
                found = false;

                for (int i = 0; i < solution.length(); i++) {
                    tryRemoveIndex(input, solution, i);
                }

                if (!found) {
                    break;
                }
            }
            return solution;
        }

        private void tryRemoveIndex(List<String> input, String inputSolution, int index) {
            String candidateSolution = inputSolution.substring(0, index) + inputSolution.substring(index + 1);
            for (String word: input) {
                if (!candidateSolution.contains(word)) return;
            }
            found = true;
            solution = candidateSolution;
        }
    }
}