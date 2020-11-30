package path;

import java.util.*;

public class LongPath {
    public static int SIZE = 200;
    private static int[] dx = {1, 0, 0, -1};
    private static int[] dy = {0, 1, -1, 0};

    public List<Integer> placeModules(List<Integer> dimensions) {
        List<Integer> solutionShortestPath = solveShortestPath(dimensions);
        if (solutionShortestPath.size() == dimensions.size()) {
            return solutionShortestPath;
        }
        return solveGreedy(dimensions);
    }

    List<Integer> solveShortestPath(List<Integer> dimensions) {
        int k = 0;
        int lastX = 0;
        int lastY = 0;
        List<Integer> output = new ArrayList<>();
        int[][] visited = new int[SIZE][SIZE];
        boolean[][] placed = new boolean[SIZE][SIZE];

        int iter = 0;
        while (k < dimensions.size()) {
            iter++;
            int h = dimensions.get(k);
            int w = dimensions.get(k + 1);

            // Always place first in top left
            if (k == 0) {
                place(0, 0, h, w, placed);
                output.add(0);
                output.add(0);
                k += 2;
                continue;
            }

            // Placing
            Queue<Pair> q = new LinkedList<>();
            q.add(new Pair(lastX, lastY));
            visited[lastX][lastY] = iter;
            while (!q.isEmpty()) {
                Pair front = q.poll();

                for (int i = 0; i < dx.length; i++) {
                    int newX = front.x + dx[i];
                    int newY = front.y + dy[i];
                    if (isNotInside(newX, newY) || visited[newX][newY] == iter) {
                        continue;
                    }
                    if (canPlace(newX, newY, h, w, placed)) {
                        q.clear();
                        lastX = newX;
                        lastY = newY;

                        output.add(lastX);
                        output.add(lastY);
                        place(lastX, lastY, h, w, placed);
                        break;
                    } else {
                        visited[newX][newY] = iter;
                        q.add(new Pair(newX, newY));
                    }
                }
            }
            k += 2;
        }
        return output;
    }

    private boolean canPlace(int x, int y, int h, int w, boolean[][] placed) {
        if (isNotInside(x, y) || placed[x][y]) return false;
        for (int i = x; i < x + h; i++) {
            for (int j = y; j < y + w; j++) {
                if (i >= SIZE || j >= SIZE || placed[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isNotInside(int x, int y) {
        return x < 0 || x >= SIZE || y < 0 || y >= SIZE;
    }

    private void place(int x, int y, int h, int w, boolean[][] placed) {
        for (int i = x; i < x + h; i++) {
            for (int j = y; j < y + w; j++) {
                placed[i][j] = true;
            }
        }
    }

    private static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    List<Integer> solveGreedy(List<Integer> dimensions) {
        int k = 0;
        List<Integer> output = new ArrayList<>();

        int iter = 0;
        for (int i = 0; i <= SIZE - 5 && k < dimensions.size(); ) {
            int maxHeight = 0;

            if (iter++ % 2 == 0) {
                for (int j = 0; j <= SIZE - 5 && k < dimensions.size(); ) {
                    output.add(i);
                    output.add(j);
                    maxHeight = Math.max(maxHeight, dimensions.get(k));
                    j += dimensions.get(k + 1);
                    k += 2;
                }
            } else {
                for (int j = SIZE - 5; j >= 0 && k < dimensions.size(); ) {
                    output.add(i);
                    output.add(j);
                    maxHeight = Math.max(maxHeight, dimensions.get(k));
                    if (k + 3 < dimensions.size()) {
                        j -= dimensions.get(k + 3);
                    }
                    k += 2;
                }
            }
            i += maxHeight;
        }
        return output;
    }
}
